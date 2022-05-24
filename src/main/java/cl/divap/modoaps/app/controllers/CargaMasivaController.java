package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.helpers.string.HelperString;
import cl.divap.modoaps.app.helpers.string.HelperValidaRut;
import cl.divap.modoaps.app.models.dao.comuna.IComunaDao;
import cl.divap.modoaps.app.models.dao.establecimiento.IEstablecimientoDao;
import cl.divap.modoaps.app.models.entity.*;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/carga-masiva")
public class CargaMasivaController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Autowired
    private IComunaDao comunaDao;

    @Autowired
    private IEstablecimientoDao establecimientoDao;

    @GetMapping(value={"", "/", "/index", "carga-archivo"})
    public String index(Model model) {
        //
        Contrato contrato = new Contrato();

        model.addAttribute("titulo", "Ingreso Dotación - Carga masiva de registro");
        model.addAttribute("contrato", contrato);

        return "carga-masiva/index";
    }

    @PostMapping(value="carga-archivo")
    public String cargaArchivo(@ModelAttribute Contrato contrato, RedirectAttributes flash, Model model, Authentication auth) throws IOException, ParseException {
        //
        List<Contrato> contratoArrayList = new ArrayList<Contrato>();

        List<Funcionario> funcionarioArrayList = new ArrayList<Funcionario>();
        Funcionario funcionario = new Funcionario();

        MultipartFile file = contrato.getCargaMasiva();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String extensionV2 = Files.getFileExtension(file.getOriginalFilename());

        if(extension.equalsIgnoreCase("xlsx")) {
            //
            ArrayList<String> errorMessages = new ArrayList<String>();
            boolean errorFlag = false;

            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            int rowsNumber = sheet.getLastRowNum();

            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()) {
                //
                Row row = rows.next();

                Long numeroRegistro = null;
                if(row.getCell(0).getCellType() == CellType.NUMERIC) {
                    numeroRegistro = (long) row.getCell(0).getNumericCellValue();
                } else {
                    errorFlag = true;
                    errorMessages.add("Número de Registro con formato inválido, debe de ser de tipo numérico");
                }

                Long idServicioLong = null;
                String servicioSalud = null;
                if(row.getCell(1).getCellType() == CellType.STRING) {
                    servicioSalud = row.getCell(1).getStringCellValue();
                    List idServicioList = funcionarioService.getIdServicioByServicioName(servicioSalud);
                    if (!idServicioList.isEmpty() && idServicioList != null) {
                        Object idServicioObject = idServicioList.get(0);
                        idServicioLong = Long.parseLong(idServicioObject.toString());
                        ServicioSalud servicioSaludEntity = new ServicioSalud();
                        servicioSaludEntity.setId(idServicioLong);
                        contrato.setServicioSalud(servicioSaludEntity);
                    } else {
                        idServicioLong = null;
                        errorFlag = true;
                        errorMessages.add("El Servicio de Salud '" + servicioSalud + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Servicio de Salud con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                Long idComunaLong = null;
                String comuna = null;
                if(row.getCell(2).getCellType() == CellType.STRING) {
                    comuna = row.getCell(2).getStringCellValue();
                    List idComunaList = funcionarioService.getIdComunaByComunaName(comuna);
                    if (!idComunaList.isEmpty() && idComunaList != null) {
                        Object idComunaObject = idComunaList.get(0);
                        idComunaLong = Long.parseLong(idComunaObject.toString());
                        Comuna comunaEntity = comunaDao.findByCodigoComuna(idComunaLong);
                        contrato.setComuna(comunaEntity);
                    } else {
                        idComunaLong = null;
                        errorFlag = true;
                        errorMessages.add("La Comuna '" + comuna + "' no es válida, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Comuna con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                Long idEstablecimientoLong = null;
                int idEstablecimientoInteger = 0;
                String establecimiento = null;
                if(row.getCell(3).getCellType() == CellType.STRING) {
                    establecimiento = row.getCell(3).getStringCellValue();
                    try {
                        Object idEstablecimientoObject = funcionarioService.getIdEstablecimientoByEstablecimientoName(establecimiento);
                        idEstablecimientoLong = Long.parseLong(idEstablecimientoObject.toString());
                        idEstablecimientoInteger = Integer.parseInt(idEstablecimientoObject.toString());
                        Establecimiento establecimientoEntity = establecimientoDao.findByCodigoNuevo(idEstablecimientoInteger);
                        contrato.setEstablecimiento(establecimientoEntity);
                    } catch (Exception ex) {
                        idEstablecimientoInteger = 0;
                        idEstablecimientoLong = null;
                        errorFlag = true;
                        errorMessages.add("El Establecimiento '" + establecimiento + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Establecimiento con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String adminSalud = null;
                Long idAdminSaludLong = null;
                if(row.getCell(4).getCellType() == CellType.STRING) {
                    adminSalud = row.getCell(4).getStringCellValue();
                    Object idAdminSaludObject = funcionarioService.getIdAdminSaludByAdminSaludName(adminSalud);
                    if(!ObjectUtils.isEmpty(idAdminSaludObject) && idAdminSaludObject != null) {
                        idAdminSaludLong = Long.parseLong(idAdminSaludObject.toString());
                        AdministracionSalud adminSaludEntity = new AdministracionSalud();
                        adminSaludEntity.setId(idAdminSaludLong);
                        contrato.setAdminSalud(adminSaludEntity);
                    } else {
                        idAdminSaludLong = null;
                        errorFlag = true;
                        errorMessages.add("La Administración Salud '" + adminSalud + "' no es válida, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Administración Salud con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String rut = null;
                String dv = null;
                String rutCompleto = null;
                boolean rutBooleanResult = false;
                boolean ifExistFuncionarioRut = true;
                boolean rutBooleanFlag = true;
                if(row.getCell(5).getCellType() == CellType.STRING && row.getCell(6).getCellType() == CellType.STRING) {
                    rut = row.getCell(5).getStringCellValue();
                    dv = row.getCell(6).getStringCellValue();
                    rutCompleto = rut + "-" + dv;
                    rutBooleanResult = HelperValidaRut.validarRut(rutCompleto);
                    if(rutBooleanResult) {
                        ifExistFuncionarioRut = funcionarioService.ifExistFuncionarioRut(rutCompleto);
                        if(ifExistFuncionarioRut) {
                            rutBooleanFlag = true;
                        } else {
                            rutBooleanFlag = false;
                            funcionario.setRun(rutCompleto);
                        }
                    } else {
                        errorFlag = true;
                        errorMessages.add("El Run '" + rutCompleto + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Run o Dv con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String apellidoPaterno = null;
                String apellidoPaternoFormat = null;
                if(row.getCell(7).getCellType() == CellType.STRING) {
                    apellidoPaterno = row.getCell(7).getStringCellValue();
                    apellidoPaternoFormat = HelperString.toTitleCase(apellidoPaterno.trim().replaceAll(" +", " ")).toUpperCase();
                    funcionario.setApellidoPaterno(apellidoPaternoFormat);
                } else {
                    errorFlag = true;
                    errorMessages.add("Apellido Paterno con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String apellidoMaterno = null;
                String apellidoMaternoFormat = null;
                if(row.getCell(8).getCellType() == CellType.STRING) {
                    apellidoMaterno = row.getCell(8).getStringCellValue();
                    apellidoMaternoFormat = HelperString.toTitleCase(apellidoMaterno.trim().replaceAll("\\s{2,}", " ")).toUpperCase();
                    funcionario.setApellidoMaterno(apellidoMaternoFormat);
                } else {
                    errorFlag = true;
                    errorMessages.add("Apellido Materno con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String nombres = null;
                String nombresFormat = null;
                if(row.getCell(9).getCellType() == CellType.STRING) {
                    nombres = row.getCell(9).getStringCellValue();
                    nombresFormat = HelperString.toTitleCase(nombres.trim().replaceAll("\\s{2,}", " ")).toUpperCase();
                    funcionario.setNombres(nombresFormat);
                } else {
                    errorFlag = true;
                    errorMessages.add("Nombres con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String sexo = null;
                Long idSexoLong = null;
                if(row.getCell(10).getCellType() == CellType.STRING) {
                    sexo = row.getCell(10).getStringCellValue();
                    Object idSexoObject = funcionarioService.getIdSexoBySexoName(sexo);
                    if(!ObjectUtils.isEmpty(idSexoObject) && idSexoObject != null) {
                        idSexoLong = Long.parseLong(idSexoObject.toString());
                        Sexo sexoEntity = new Sexo();
                        sexoEntity.setId(idSexoLong);
                        funcionario.setSexo(sexoEntity);
                    } else {
                        idSexoLong = null;
                        errorFlag = true;
                        errorMessages.add("El Sexo '" + sexo + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Sexo con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String fechaNacimientoString = null;
                Date fechaNacimientoDate = null;
                String fechaNacimientoStringFormat = null;
                if(row.getCell(11).getCellType() == CellType.STRING) {
                    fechaNacimientoString = row.getCell(11).getStringCellValue();    // 1957-02-27
                    try {
                        fechaNacimientoDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimientoString); // Wed Feb 27 00:00:00 CLT 1957
                        funcionario.setFechaNacimiento(fechaNacimientoDate);

                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        fechaNacimientoStringFormat = simpleDateFormat.format(fechaNacimientoDate);
                    } catch (Exception e) {
                        errorFlag = true;
                        errorMessages.add("La Fecha de Nacimiento '" + fechaNacimientoString + "' no es válido, en linea N° " + numeroRegistro + ", ingrese formato 'yyyy-MM-dd'");
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Fecha de Nacimiento con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro + ", ingrese formato 'yyyy-MM-dd'");
                }

                String nacionalidad = null;
                Long idNacionalidadLong = null;
                if(row.getCell(12).getCellType() == CellType.STRING) {
                    nacionalidad = row.getCell(12).getStringCellValue();
                    Object idNacionalidadObject = funcionarioService.getIdNacionalidadByNacionalidadName(nacionalidad);
                    if(!ObjectUtils.isEmpty(idNacionalidadObject) && idNacionalidadObject != null) {
                        idNacionalidadLong = Long.parseLong(idNacionalidadObject.toString());
                        Nacionalidad nacionalidadEntity = new Nacionalidad();
                        nacionalidadEntity.setId(idNacionalidadLong);
                        funcionario.setNacionalidad(nacionalidadEntity);
                    } else {
                        idSexoLong = null;
                        errorFlag = true;
                        errorMessages.add("La Nacionalidad '" + nacionalidad + "' no es válida, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Nacionalidad con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String ley = null;
                Long idLeyLong = null;
                if(row.getCell(13).getCellType() == CellType.STRING) {
                    ley = row.getCell(13).getStringCellValue();
                    Object idLeyObject = funcionarioService.getIdLeyByLeyName(ley);
                    if(!ObjectUtils.isEmpty(idLeyObject) && idLeyObject != null) {
                        idLeyLong = Long.parseLong(idLeyObject.toString());
                        Ley leyEntity = new Ley();
                        leyEntity.setId(idLeyLong);
                        contrato.setLey(leyEntity);
                    } else {
                        idLeyLong = null;
                        errorFlag = true;
                        errorMessages.add("La Ley '" + ley + "' no es válida, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Ley con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String tipoContrato = null;
                Long idTipoContratoLong = null;
                if(row.getCell(14).getCellType() == CellType.STRING) {
                    tipoContrato = row.getCell(14).getStringCellValue();
                    if(idLeyLong != null) {
                        Object idTipoContratoObject = funcionarioService.getIdTipoContratoByTipoContratoNameAndIdLeyLong(tipoContrato, idLeyLong);
                        if(!ObjectUtils.isEmpty(idTipoContratoObject) && idTipoContratoObject != null) {
                            idTipoContratoLong = Long.parseLong(idTipoContratoObject.toString());
                            TipoContrato tipoContratoEntity = new TipoContrato();
                            tipoContratoEntity.setId(idTipoContratoLong);
                            contrato.setTipoContrato(tipoContratoEntity);
                        } else {
                            idTipoContratoLong = null;
                            errorFlag = true;
                            errorMessages.add("El Tipo de Contrato '" + tipoContrato + "' no es válido, en linea N° " + numeroRegistro);
                        }
                    } else {
                        idTipoContratoLong = null;
                        errorFlag = true;
                        errorMessages.add("El Tipo de Contrato '" + tipoContrato + "', no tiene ley definida o esta última es inválida, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Tipo de Contrato con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String categoriaProfesion = null;
                Long idCategoriaProfesionLong = null;
                if(row.getCell(15).getCellType() == CellType.STRING) {
                    categoriaProfesion = row.getCell(15).getStringCellValue();
                    if(idLeyLong != null && idLeyLong == 1) {
                        Object idCategoriaProfesionOject = funcionarioService.getIdCategoriaProfesionByCategoriaProfesionName(categoriaProfesion);
                        if (!ObjectUtils.isEmpty(idCategoriaProfesionOject) && idCategoriaProfesionOject != null) {
                            idCategoriaProfesionLong = Long.parseLong(idCategoriaProfesionOject.toString());
                            CategoriaProfesion categoriaProfesionEntity = new CategoriaProfesion();
                            categoriaProfesionEntity.setId(idCategoriaProfesionLong);
                            contrato.setCategoriaProfesion(categoriaProfesionEntity);
                        } else {
                            idCategoriaProfesionLong = null;
                            errorFlag = true;
                            errorMessages.add("La Categoría de Profesión '" + categoriaProfesion + "' no es válida o no corresponde a la ley '" + ley + "', en linea N° " + numeroRegistro);
                        }
                    } else if(idLeyLong != null && (idLeyLong == 2 || idLeyLong == 3)) {
                        Object idCategoriaProfesionObject = funcionarioService.getIdCategoriaProfesionByCategoriaProfesionNameLeyHonorarioAndCodigoTrabajo(categoriaProfesion);
                        if (!ObjectUtils.isEmpty(idCategoriaProfesionObject) && idCategoriaProfesionObject != null) {
                            idCategoriaProfesionLong = Long.parseLong(idCategoriaProfesionObject.toString());
                            CategoriaProfesion categoriaProfesionEntity = new CategoriaProfesion();
                            categoriaProfesionEntity.setId(idCategoriaProfesionLong);
                            contrato.setCategoriaProfesion(categoriaProfesionEntity);
                        } else {
                            idCategoriaProfesionLong = null;
                            errorFlag = true;
                            errorMessages.add("La Categoría de Profesión '" + categoriaProfesion + "' no es válida o no corresponde a la ley '" + ley + "', en linea N° " + numeroRegistro);
                        }
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Categoría de Profesión con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String nivelCarrera = null;
                Long idNivelCarreraLong = null;
                if(row.getCell(16).getCellType() == CellType.STRING) {
                    nivelCarrera = row.getCell(16).getStringCellValue();
                    if(nivelCarrera.equals("N/A")) {
                        nivelCarrera = "0";
                    }
                    if(idLeyLong != null && idLeyLong == 1) {
                        Object idNivelCarreraObject = funcionarioService.getIdNivelCarreraByNivelCarreraNameLey19378(nivelCarrera);
                        if (!ObjectUtils.isEmpty(idNivelCarreraObject) && idNivelCarreraObject != null) {
                            idNivelCarreraLong = Long.parseLong(idNivelCarreraObject.toString());
                            NivelCarrera nivelCarreraEntity = new NivelCarrera();
                            nivelCarreraEntity.setId(idNivelCarreraLong);
                            contrato.setNivelCarrera(nivelCarreraEntity);
                        } else {
                            idNivelCarreraLong = null;
                            errorFlag = true;
                            errorMessages.add("El Nivel de Carrera '" + nivelCarrera + "' no es válida o no corresponde a la ley '" + ley + "', en linea N° " + numeroRegistro);
                        }
                    } else if(idLeyLong != null && (idLeyLong == 2 || idLeyLong == 3)) {
                        Object idNivelCarreraObject = funcionarioService.getIdNivelCarreraByNivelCarreraNameLeyHonorarioAndCodigoTrabajo(nivelCarrera);
                        if (!ObjectUtils.isEmpty(idNivelCarreraObject) && idNivelCarreraObject != null) {
                            idNivelCarreraLong = Long.parseLong(idNivelCarreraObject.toString());
                            NivelCarrera nivelCarreraEntity = new NivelCarrera();
                            nivelCarreraEntity.setId(idNivelCarreraLong);
                            contrato.setNivelCarrera(nivelCarreraEntity);
                        } else {
                            idNivelCarreraLong = null;
                            errorFlag = true;
                            errorMessages.add("El Nivel de Carrera '" + nivelCarrera + "' no es válida o no corresponde a la ley '" + ley + "', en linea N° " + numeroRegistro);
                        }
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Nivel de Carrera con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String profesion = null;
                Long idProfesionLong = null;
                if(row.getCell(17).getCellType() == CellType.STRING) {
                    profesion = row.getCell(17).getStringCellValue();
                    if(idLeyLong != null && idLeyLong == 1) {
                        Object idProfesionObject = funcionarioService.getIdProfesionByProfesionNameAndIdCategoriaProfesionLong(profesion, idCategoriaProfesionLong);
                        if (!ObjectUtils.isEmpty(idProfesionObject) && idProfesionObject != null) {
                            idProfesionLong = Long.parseLong(idProfesionObject.toString());
                            Profesion profesionEntity = new Profesion();
                            profesionEntity.setId(idProfesionLong);
                            contrato.setProfesion(profesionEntity);
                        } else {
                            idProfesionLong = null;
                            errorFlag = true;
                            categoriaProfesion = categoriaProfesion != null ? categoriaProfesion : "inválida";
                            errorMessages.add("La Profesión '" + profesion + "' no es válida o no corresponde a la ley '" + ley + "' con categoría '" + categoriaProfesion + "', en linea N° " + numeroRegistro);
                        }
                    } else if(idLeyLong != null && (idLeyLong == 2 || idLeyLong == 3)) {
                        Object idProfesionObject = funcionarioService.getIdProfesionByProfesionNameAndIdCategoriaProfesionLongLeyHonorarioAndCodigoTrabajo(profesion, idCategoriaProfesionLong);
                        if (!ObjectUtils.isEmpty(idProfesionObject) && idProfesionObject != null) {
                            idProfesionLong = Long.parseLong(idProfesionObject.toString());
                            Profesion profesionEntity = new Profesion();
                            profesionEntity.setId(idProfesionLong);
                            contrato.setProfesion(profesionEntity);
                        } else {
                            idProfesionLong = null;
                            errorFlag = true;
                            errorMessages.add("La Profesión '" + profesion + "' no es válida o no corresponde a la ley '" + ley + "' con categoría '" + categoriaProfesion + "', en linea N° " + numeroRegistro);
                        }
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Profesión con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String especialidad = null;
                Long idEspecialidadLong = null;
                if(row.getCell(18).getCellType() == CellType.STRING) {
                    especialidad = row.getCell(18).getStringCellValue();
                    Object idEspecialidadObject = funcionarioService.getIdEspecialidadByEspecialidadNameAndIdProfesionLong(especialidad, idProfesionLong);
                    if (!ObjectUtils.isEmpty(idEspecialidadObject) && idEspecialidadObject != null) {
                        idEspecialidadLong = Long.parseLong(idEspecialidadObject.toString());
                        Especialidad especialidadEntity = new Especialidad();
                        especialidadEntity.setId(idEspecialidadLong);
                        contrato.setEspecialidad(especialidadEntity);
                    } else {
                        idEspecialidadLong = null;
                        errorFlag = true;
                        errorMessages.add("La Especialidad '" + especialidad + "' no es válida o la ley es inválida o la categoría es inválida o la profesión es inválida, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Especialidad con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String cargo = null;
                Long idCargoLong = null;
                if(row.getCell(19).getCellType() == CellType.STRING) {
                    cargo = row.getCell(19).getStringCellValue();
                    Object idCargoObject = funcionarioService.getIdCargoByCargoName(cargo);
                    if (!ObjectUtils.isEmpty(idCargoObject) && idCargoObject != null) {
                        idCargoLong = Long.parseLong(idCargoObject.toString());
                        Cargo cargoEntity = new Cargo();
                        cargoEntity.setId(idCargoLong);
                        contrato.setCargo(cargoEntity);
                    } else {
                        idCargoLong = null;
                        errorFlag = true;
                        errorMessages.add("El Cargo '" + cargo + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Cargo con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String asignacionChofer = null;
                Long idAsignacionChoferLong = null;
                if(row.getCell(20).getCellType() == CellType.STRING) {
                    asignacionChofer = row.getCell(20).getStringCellValue();
                    Object idAsignacionChoferObject = funcionarioService.getIdAsignacionChoferByAsignacionChoferNameAndIdCargoLong(asignacionChofer, idCargoLong);
                    if (!ObjectUtils.isEmpty(idAsignacionChoferObject) && idAsignacionChoferObject != null) {
                        idAsignacionChoferLong = Long.parseLong(idAsignacionChoferObject.toString());
                        AsignacionChofer asignacionChoferEntity = new AsignacionChofer();
                        asignacionChoferEntity.setId(idAsignacionChoferLong);
                        contrato.setAsignacionChofer(asignacionChoferEntity);
                    } else {
                        idAsignacionChoferLong = null;
                        errorFlag = true;
                        errorMessages.add("La Asignación Chofer '" + asignacionChofer + "' no es válida o el cargo es inválido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Asignación Chofer con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                Integer jornadaLaboral = null;
                Long jornadaLaboralLong = null;
                Long countJornadaLaboraLong = 0L;
                Long totalJornadaLong = 0L;
                if(row.getCell(21).getCellType() == CellType.NUMERIC) {
                    jornadaLaboral = (int) row.getCell(21).getNumericCellValue();
                    jornadaLaboralLong = Long.valueOf(jornadaLaboral);
                    Object countJornadaLaboraObject = funcionarioService.getCountJornadaLaboralByRutFuncionario(rutCompleto);
                    if (!ObjectUtils.isEmpty(countJornadaLaboraObject) && countJornadaLaboraObject != null) {
                        countJornadaLaboraLong = Long.parseLong(countJornadaLaboraObject.toString());
                    }
                    totalJornadaLong = countJornadaLaboraLong + jornadaLaboralLong;
                    if(idLeyLong != null && (idLeyLong == 1 || idLeyLong == 3) && (totalJornadaLong <= 44 && totalJornadaLong > 0)) {
                        contrato.setJornadaLaboral(jornadaLaboral);
                        System.out.println("Jornada Laboral Ok, dentro del rango [1 - 44] horas (Ley 19.378 o Código del Trabajo)");
                    } else if(idLeyLong != null && (idLeyLong == 1 || idLeyLong == 3) && (totalJornadaLong > 44 || totalJornadaLong < 1)) {
                        errorFlag = true;
                        errorMessages.add("En Base de Datos sumado a linea Excel N° " + numeroRegistro + ", la Jornada Laboral con ley '" + ley + "' no puede ser mayor a 44 horas ni menor a 1 hora, actualmente suma un total de " + totalJornadaLong + " horas, en linea N° " + numeroRegistro);
                        System.out.println("En Base de Datos sumado a linea Excel N° " + numeroRegistro + ", la Jornada Laboral con ley '" + ley + "' no puede ser mayor a 44 horas ni menor a 1 hora, actualmente suma un total de " + totalJornadaLong + " horas, en linea N° " + numeroRegistro);
                    }
                    if(idLeyLong != null && (idLeyLong == 2 && totalJornadaLong > 0)) {
                        contrato.setJornadaLaboral(jornadaLaboral);
                        System.out.println("Jornada Laboral Ok, dentro del rango [ mayor o igual a 1] (Honorario)");
                    } else if(idLeyLong != null && (idLeyLong == 2 && totalJornadaLong < 1)) {
                        errorFlag = true;
                        errorMessages.add("La Jornada Laboral con ley '" + ley + "' no puede ser menor a 1 hora, actualmente suma un total de " + totalJornadaLong + " horas, en linea N° " + numeroRegistro);
                        System.out.println("Jornada Laboral fuera de rango [ menor a 1 ], NO Ok (Honorario)");
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Jornada Laboral con formato inválido, debe de ser de tipo numérico, en linea N° " + numeroRegistro);
                }

                String aniosServicio = null;
                Integer aniosServicioInteger = null;
                if(row.getCell(22).getCellType() == CellType.STRING) {
                    aniosServicio = row.getCell(22).getStringCellValue();
                    aniosServicioInteger = Integer.parseInt(aniosServicio);
                    contrato.setAniosServicio(aniosServicioInteger);
                } else {
                    errorFlag = true;
                    errorMessages.add("Años de Servicio con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String fechaIngresoString = null;
                Date fechaIngresoDate = null;
                String fechaIngresoStringFormat = null;
                if(row.getCell(23).getCellType() == CellType.STRING) {
                    fechaIngresoString = row.getCell(23).getStringCellValue();    // 1957-02-27
                    try {
                        fechaIngresoDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaIngresoString); // Wed Feb 27 00:00:00 CLT 1957
                        contrato.setFechaIngreso(fechaIngresoDate);

                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        fechaIngresoStringFormat = simpleDateFormat.format(fechaIngresoDate);
                    } catch (Exception e) {
                        errorFlag = true;
                        errorMessages.add("La Fecha de Ingreso '" + fechaIngresoString + "' no es válido, en linea N° " + numeroRegistro + ", ingrese formato 'yyyy-MM-dd'");
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Fecha de Ingreso con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro + ", ingrese formato 'yyyy-MM-dd'");
                }

                String bienios = null;
                Long idBieniosLong = null;
                if(row.getCell(24).getCellType() == CellType.STRING) {
                    bienios = row.getCell(24).getStringCellValue();
                    Object idBieniosObject = funcionarioService.getIdBieniosByBieniosName(bienios);
                    if (!ObjectUtils.isEmpty(idBieniosObject) && idBieniosObject != null) {
                        idBieniosLong = Long.parseLong(idBieniosObject.toString());
                        Bienios bieniosEntity = new Bienios();
                        bieniosEntity.setId(idBieniosLong);
                        contrato.setBienios(bieniosEntity);
                    } else {
                        idBieniosLong = null;
                        errorFlag = true;
                        errorMessages.add("El Bienio '" + bienios + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Bienio con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String prevision = null;
                Long idPrevisionLong = null;
                if(row.getCell(25).getCellType() == CellType.STRING) {
                    prevision = row.getCell(25).getStringCellValue();
                    Object idPrevisionObject = funcionarioService.getIdPrevisionByPrevisionName(prevision);
                    if (!ObjectUtils.isEmpty(idPrevisionObject) && idPrevisionObject != null) {
                        idPrevisionLong = Long.parseLong(idPrevisionObject.toString());
                        Prevision previsionEntity = new Prevision();
                        previsionEntity.setId(idPrevisionLong);
                        contrato.setPrevision(previsionEntity);
                    } else {
                        idPrevisionLong = null;
                        errorFlag = true;
                        errorMessages.add("La Previsión '" + prevision + "' no es válida, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Previsión con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String isapre = null;
                Long idIsapreLong = null;
                if(row.getCell(26).getCellType() == CellType.STRING) {
                    isapre = row.getCell(26).getStringCellValue();
                    Object idIsapreObject = funcionarioService.getIdIsapreByIsapreName(isapre);
                    if (!ObjectUtils.isEmpty(idIsapreObject) && idIsapreObject != null) {
                        idIsapreLong = Long.parseLong(idIsapreObject.toString());
                        Isapre isapreEntity = new Isapre();
                        isapreEntity.setId(idIsapreLong);
                        contrato.setIsapre(isapreEntity);
                    } else {
                        idIsapreLong = null;
                        errorFlag = true;
                        errorMessages.add("La Isapre '" + isapre + "' no es válida, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Isapre con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String sueldoBase = null;
                Long sueldoBaseLong = null;
                if(row.getCell(27).getCellType() == CellType.STRING) {
                    sueldoBase = row.getCell(27).getStringCellValue();
                    try {
                        sueldoBaseLong = Long.parseLong(sueldoBase);
                        contrato.setSueldoBase(sueldoBaseLong);
                    } catch (NumberFormatException e) {
                        sueldoBaseLong = null;
                        errorFlag = true;
                        errorMessages.add("El Sueldo Base '" + sueldoBase + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Sueldo Base con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String totalHaberes = null;
                Long totalHaberesLong = null;
                if(row.getCell(28).getCellType() == CellType.STRING) {
                    totalHaberes = row.getCell(28).getStringCellValue();
                    try {
                        totalHaberesLong = Long.parseLong(totalHaberes);
                        contrato.setTotalHaberes(totalHaberesLong);
                    } catch (NumberFormatException e) {
                        totalHaberesLong = null;
                        errorFlag = true;
                        errorMessages.add("El Total de Haberes '" + totalHaberes + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Total Haberes con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String validado = null;
                boolean validadoBoolean = false;
                if(row.getCell(29).getCellType() == CellType.STRING) {
                    validado = row.getCell(29).getStringCellValue();
                    if(validado.equals("Si")) {
                        validadoBoolean = true;
                        contrato.setValidado(validadoBoolean);
                    } else if(validado.equals("No")) {
                        validadoBoolean = false;
                        contrato.setValidado(validadoBoolean);
                    } else {
                        validadoBoolean = false;
                        errorFlag = true;
                        errorMessages.add("El Campo Validado '" + validado + "' no es válido, debe de ser 'Si' o 'No', en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Campo Validado con formato inválido, debe de ser de tipo texto ('Si' o 'No'), en linea N° " + numeroRegistro);
                }

                String revisado = null;
                boolean revisadoBoolean = false;
                if(row.getCell(30).getCellType() == CellType.STRING) {
                    revisado = row.getCell(30).getStringCellValue();
                    if(revisado.equals("Si")) {
                        revisadoBoolean = true;
                        contrato.setRevisado(revisadoBoolean);
                    } else if(revisado.equals("No")) {
                        revisadoBoolean = false;
                        contrato.setRevisado(revisadoBoolean);
                    } else {
                        revisadoBoolean = false;
                        errorFlag = true;
                        errorMessages.add("El Campo Revisado '" + revisado + "' no es válido, debe de ser 'Si' o 'No', en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Campo Revisado con formato inválido, debe de ser de tipo texto ('Si' o 'No'), en linea N° " + numeroRegistro);
                }

                if(rutCompleto != null && rutBooleanResult) {
                    List<Contrato> contratosDB = funcionarioService.getContratosByRutFuncionarioWithFuncionario(rutCompleto);

                    Long idFuncionarioLong = null;
                    Object idFuncionarioObject = funcionarioService.getIdFuncionarioByRutFuncionario(rutCompleto);
                    if (!ObjectUtils.isEmpty(idFuncionarioObject) && idFuncionarioObject != null) {
                        idFuncionarioLong = Long.parseLong(idFuncionarioObject.toString());
                    } else {
                        idFuncionarioLong = null;
                    }

                    if(!contratosDB.isEmpty() && contratosDB != null) {
                        for (Contrato condb : contratosDB) {
                            System.out.print("Base de Datos:: " + "Id Funcionario: " + condb.getFuncionario().getId() + " | Servicio Id: " + condb.getServicioSalud().getId() + " | Código Comuna: " + condb.getComuna().getCodigoComuna());
                            System.out.print(" | Cod Nuevo Establecimiento: " + condb.getEstablecimiento().getCodigoNuevo() + " | Admin: " + condb.getAdminSalud().getId() + " | Run Funcionario: " + condb.getFuncionario().getRun());
                            System.out.print(" | Ley: " + condb.getLey().getId() + " | Tipo Contrato: " + condb.getTipoContrato().getId() + " | Categoría: " + condb.getCategoriaProfesion().getId() + " | Nivel Carrera: " + condb.getNivelCarrera().getId());
                            System.out.print(" | Profesión: " + condb.getProfesion().getId() + " | Especialidad: " + condb.getEspecialidad().getId() + " | Cargo: " + condb.getCargo().getId() + " | Asig. Chofer: " + condb.getAsignacionChofer().getId());
                            System.out.print(" | Jornada: " + condb.getJornadaLaboral() + " | Años de Serv.:" + condb.getAniosServicio() + " | Fecha Ingreso: " + condb.getFechaIngreso().toString() + " | Bienios: " + condb.getBienios().getId());
                            System.out.print(" | Previsión: " + condb.getPrevision().getId() + " | Isapre: " + condb.getIsapre().getId() + " | Sueldo Base: " + condb.getSueldoBase() + " | Total Haberes: " + condb.getTotalHaberes());
                            System.out.print(" | Validado: " + condb.getValidado() + " | Revisado: " + condb.getRevisado());
                            System.out.println();

                            if (condb.getFuncionario().getId().equals(idFuncionarioLong) && condb.getServicioSalud().getId().equals(idServicioLong) && condb.getComuna().getCodigoComuna().equals(idComunaLong) &&
                                    condb.getEstablecimiento().getCodigoNuevo() == idEstablecimientoInteger && condb.getAdminSalud().getId().equals(idAdminSaludLong) && condb.getFuncionario().getRun().equals(rutCompleto) &&
                                    condb.getLey().getId().equals(idLeyLong) && condb.getTipoContrato().getId().equals(idTipoContratoLong) && condb.getCategoriaProfesion().getId().equals(idCategoriaProfesionLong) && condb.getNivelCarrera().getId().equals(idNivelCarreraLong) &&
                                    condb.getProfesion().getId().equals(idProfesionLong) && condb.getEspecialidad().getId().equals(idEspecialidadLong) && condb.getCargo().getId().equals(idCargoLong) && condb.getAsignacionChofer().getId().equals(idAsignacionChoferLong) &&
                                    condb.getJornadaLaboral().equals(jornadaLaboral) && condb.getAniosServicio().equals(aniosServicioInteger) && condb.getFechaIngreso().toString().equals(fechaIngresoString) && condb.getBienios().getId().equals(idBieniosLong) &&
                                    condb.getPrevision().getId().equals(idPrevisionLong) && condb.getIsapre().getId().equals(idIsapreLong) && condb.getSueldoBase().equals(sueldoBaseLong) && condb.getTotalHaberes().equals(totalHaberesLong) &&
                                    condb.getValidado() == validadoBoolean && condb.getRevisado() == revisadoBoolean
                            ) {
                                System.out.println("El contrato ya existe asociado al funcionario " + nombresFormat + " " + apellidoPaternoFormat + " " + apellidoMaternoFormat + " (" + rutCompleto + ") en la base de datos");
                                errorMessages.add("El contrato ya existe asociado al funcionario " + nombresFormat + " " + apellidoPaternoFormat + " " + apellidoMaternoFormat + " (" + rutCompleto + ") en la base de datos, en linea N° " + numeroRegistro);
                            } else {
                                System.out.println("Contrato existente en base de datos pero distinto al contrato en planilla Excell, en linea N° " + numeroRegistro);
                            }
                        }
                    } else {
                        System.out.println("El funcionario en planilla Excel no existe en la base de datos, por lo que no tiene contratos asignados!!!");
                    }

                    System.out.print("Archivo Excel:: [N°: " + numeroRegistro + "] " + "Id Funcionario: " + idFuncionarioLong + " | Servicio Id: " + idServicioLong + " | Código Comuna: " + idComunaLong);
                    System.out.print(" | Cod Nuevo Establecimiento: " + idEstablecimientoInteger + " | Admin: " + idAdminSaludLong + " | Run Funcionario: " + rutCompleto);
                    System.out.print(" | Ley: " + idLeyLong + " | Tipo Contrato: " + idTipoContratoLong + " | Categoría: " + idCategoriaProfesionLong + " | Nivel Carrera: " + idNivelCarreraLong);
                    System.out.print(" | Profesión: " + idProfesionLong + " | Especialidad: " + idEspecialidadLong + " | Cargo: " + idCargoLong + " | Asig. Chofer: " + idAsignacionChoferLong);
                    System.out.print(" | Jornada: " + jornadaLaboral + " | Años de Serv.:" + aniosServicioInteger + " | Fecha Ingreso: " + fechaIngresoString + " | Bienios: " + idBieniosLong);
                    System.out.print(" | Previsión: " + idPrevisionLong + " | Isapre: " + idIsapreLong + " | Sueldo Base: " + sueldoBaseLong + " | Total Haberes: " + totalHaberesLong);
                    System.out.print(" | Validado: " + validadoBoolean + " | Revisado: " + revisadoBoolean);
                    System.out.println();
                }

                funcionario.setCreateAt(new Date());
                funcionario.setEnabled(true);

                contrato.setCreateAt(new Date());
                contrato.setEnabled(true);
                contrato.setFechaDisabled(null);
                contrato.setFechaEdicion(null);
                contrato.setUsuarioCreador(auth.getName());
                contrato.setUsuarioEditor(null);
                contrato.setFechaCarga(new Date());
                contrato.setTipoIngresoRegistro("Carga Masiva");
                contrato.setFechaValidacion(null);
                contrato.setUsuarioValidador(null);
                contrato.setFechaRevision(null);
                contrato.setUsuarioRevisor(null);
                contrato.setRutFuncionario(rutCompleto);

                // System.out.println( "|__ Servicio Salud Id: " + contrato.getServicioSalud() + " | Comuna Id: " + contrato.getComuna() + " | Establecimiento Id: " + contrato.getEstablecimiento() + " __|");
                /*
                System.out.print(idServicioLong + " | " + idComunaLong + " | " + idEstablecimientoInteger + " | " + idAdminSaludLong + " | " + rutBooleanResult + " (" + ifExistFuncionarioRut + " - " + rutBooleanFlag + ") " + " | " + apellidoPaternoFormat + " | " + apellidoMaternoFormat + " | " + nombresFormat + " | ");
                System.out.print(idSexoLong + " | " + fechaNacimientoDate + " | " + fechaNacimientoStringFormat + " | " + idNacionalidadLong + " | " + idLeyLong + " | " + idTipoContratoLong + " | " + idCategoriaProfesionLong + " | ");
                System.out.print(idNivelCarreraLong + " | " + idProfesionLong + " | " + idEspecialidadLong + " | " + idCargoLong + " | " + idAsignacionChoferLong + " | Jornada DB: " + countJornadaLaboraLong + " | Jornada Excel: " + jornadaLaboralLong + " | Jornada Total: " + totalJornadaLong + " | ");
                System.out.print(aniosServicioInteger + " | " + fechaIngresoString + "(" + fechaIngresoStringFormat + ") - " + fechaIngresoDate + " | Bienio: " + idBieniosLong + " | Previsión: " + idPrevisionLong + " | Isapre: " + idIsapreLong + " | " + sueldoBaseLong + " | " + totalHaberesLong + " | ");
                System.out.print(validadoBoolean + " | " + revisadoBoolean);
                System.out.println();

                System.out.print( numeroRegistro + " | ");
                System.out.print( servicioSalud + " | ");
                System.out.print( comuna + " | ");
                System.out.print( establecimiento + " | ");
                System.out.print( adminSalud + " | ");
                System.out.print( rut + " | ");
                System.out.print( dv + " | ");
                System.out.print( rutCompleto + " | ");
                System.out.print( apellidoPaterno + " | ");
                System.out.print( apellidoMaterno + " | ");
                System.out.print( nombres + " | ");
                System.out.print( sexo + " | ");
                System.out.print( fechaNacimientoString + " | ");
                // System.out.print( fechaNacimientoDate + " | ");
                // System.out.print( dateNacimientoFormat + " | ");
                System.out.print( nacionalidad + " | ");
                System.out.print( ley + " | ");
                System.out.print( tipoContrato + " | ");
                System.out.print( categoriaProfesion + " | ");
                System.out.print( nivelCarrera + " | ");
                System.out.print( profesion + " | ");
                System.out.print( especialidad + " | ");
                System.out.print( cargo + " | ");
                System.out.print( asignacionChofer + " | ");
                System.out.print( jornadaLaboral + " | ");
                System.out.print( aniosServicio + " | ");
                System.out.print( fechaIngresoString + " | ");
                System.out.print( bienios + " | ");
                System.out.print( prevision + " | ");
                System.out.print( isapre + " | ");
                System.out.print( sueldoBase + " | ");
                System.out.print( totalHaberes + " | ");
                System.out.print( validado + " | ");
                System.out.print( revisado + " __| ");
                System.out.println();
                */

                if(!rutBooleanFlag) {
                    System.out.println("rutBooleanFlag es false, es decir el rut no existe en la base de datos");
                    funcionarioArrayList.add(new Funcionario(funcionario.getNombres(), funcionario.getApellidoPaterno(), funcionario.getApellidoMaterno(), funcionario.getRun(), funcionario.getSexo(), funcionario.getNacionalidad(), funcionario.getFechaNacimiento(), funcionario.getCreateAt(), funcionario.getEnabled()));
                } else {
                    System.out.println("rutBooleanFlag es true, es decir el rut SI existe en la base de datos");
                }

                contratoArrayList.add(new Contrato(contrato.getServicioSalud(), contrato.getComuna(), contrato.getEstablecimiento(), contrato.getAdminSalud(), contrato.getLey(), contrato.getTipoContrato(), contrato.getCategoriaProfesion(),
                        contrato.getNivelCarrera(), contrato.getProfesion(), contrato.getEspecialidad(), contrato.getCargo(), contrato.getAsignacionChofer(), contrato.getJornadaLaboral(), contrato.getAniosServicio(), contrato.getFechaIngreso(),
                        contrato.getBienios(), contrato.getPrevision(), contrato.getIsapre(), contrato.getSueldoBase(), contrato.getTotalHaberes(), contrato.getValidado(), contrato.getRevisado(), contrato.getCreateAt(), contrato.getFuncionario(),
                        contrato.getEnabled(), contrato.getFechaDisabled(), contrato.getFechaEdicion(), contrato.getUsuarioCreador(), contrato.getUsuarioEditor(), contrato.getFechaCarga(), contrato.getTipoIngresoRegistro(), contrato.getFechaValidacion(),
                        contrato.getUsuarioValidador(), contrato.getFechaRevision(), contrato.getUsuarioRevisor(), contrato.getRutFuncionario()));

            }

            /* ########################################################### [ COMIENZO: BUCLE ANIDADO: VALIDACIÓN JORNADA LABORA Y REGISTROS DUPLICADOS] ########################################################### */
            /*
            Iterator<Row> rowsFirst = sheet.iterator();
            rowsFirst.next();
            while (rowsFirst.hasNext()) {
                Row row1 = rowsFirst.next();

                Long numeroRegistro1 = null;
                if(row1.getCell(0).getCellType() == CellType.NUMERIC) {
                    numeroRegistro1 = (long) row1.getCell(0).getNumericCellValue();
                }

                Long idServicioLong1 = 0L;
                String servicioSalud1 = null;
                if(row1.getCell(1).getCellType() == CellType.STRING) {
                    servicioSalud1 = row1.getCell(1).getStringCellValue();
                    List idServicioList1 = funcionarioService.getIdServicioByServicioName(servicioSalud1);
                    if (!idServicioList1.isEmpty() && idServicioList1 != null) {
                        Object idServicioObject1 = idServicioList1.get(0);
                        idServicioLong1 = Long.parseLong(idServicioObject1.toString());
                    }
                }

                Long idComunaLong1 = 0L;
                String comuna1 = null;
                if(row1.getCell(2).getCellType() == CellType.STRING) {
                    comuna1 = row1.getCell(2).getStringCellValue();
                    List idComunaList1 = funcionarioService.getIdComunaByComunaName(comuna1);
                    if (!idComunaList1.isEmpty() && idComunaList1 != null) {
                        Object idComunaObject1 = idComunaList1.get(0);
                        idComunaLong1 = Long.parseLong(idComunaObject1.toString());
                    }
                }

                Long idEstablecimientoLong1 = 0L;
                int idEstablecimientoInteger1 = 0;
                String establecimiento1 = null;
                if(row1.getCell(3).getCellType() == CellType.STRING) {
                    establecimiento1 = row1.getCell(3).getStringCellValue();
                    try {
                        Object idEstablecimientoObject1 = funcionarioService.getIdEstablecimientoByEstablecimientoName(establecimiento1);
                        idEstablecimientoLong1 = Long.parseLong(idEstablecimientoObject1.toString());
                        idEstablecimientoInteger1 = Integer.parseInt(idEstablecimientoObject1.toString());
                    } catch (Exception ex) {
                        idEstablecimientoInteger1 = 0;
                        idEstablecimientoLong1 = null;
                    }
                }

                String adminSalud1 = null;
                Long idAdminSaludLong1 = 0L;
                if(row1.getCell(4).getCellType() == CellType.STRING) {
                    adminSalud1 = row1.getCell(4).getStringCellValue();
                    Object idAdminSaludObject1 = funcionarioService.getIdAdminSaludByAdminSaludName(adminSalud1);
                    if(!ObjectUtils.isEmpty(idAdminSaludObject1) && idAdminSaludObject1 != null) {
                        idAdminSaludLong1 = Long.parseLong(idAdminSaludObject1.toString());
                    }
                }

                String rut1 = null;
                String dv1 = null;
                String rutCompleto1 = "";
                if(row1.getCell(5).getCellType() == CellType.STRING && row1.getCell(6).getCellType() == CellType.STRING) {
                    rut1 = row1.getCell(5).getStringCellValue();
                    dv1 = row1.getCell(6).getStringCellValue();
                    rutCompleto1 = rut1 + "-" + dv1;
                }

                String ley1 = null;
                Long idLeyLong1 = 0L;
                if(row1.getCell(13).getCellType() == CellType.STRING) {
                    ley1 = row1.getCell(13).getStringCellValue();
                    Object idLeyObject1 = funcionarioService.getIdLeyByLeyName(ley1);
                    if(!ObjectUtils.isEmpty(idLeyObject1) && idLeyObject1 != null) {
                        idLeyLong1 = Long.parseLong(idLeyObject1.toString());
                    }
                }

                String tipoContrato1 = null;
                Long idTipoContratoLong1 = 0L;
                if(row1.getCell(14).getCellType() == CellType.STRING) {
                    tipoContrato1 = row1.getCell(14).getStringCellValue();
                    if(idLeyLong1 != null) {
                        Object idTipoContratoObject1 = funcionarioService.getIdTipoContratoByTipoContratoNameAndIdLeyLong(tipoContrato1, idLeyLong1);
                        if(!ObjectUtils.isEmpty(idTipoContratoObject1) && idTipoContratoObject1 != null) {
                            idTipoContratoLong1 = Long.parseLong(idTipoContratoObject1.toString());
                        }
                    }
                }

                String categoriaProfesion1 = null;
                Long idCategoriaProfesionLong1 = 0L;
                if(row1.getCell(15).getCellType() == CellType.STRING) {
                    categoriaProfesion1 = row1.getCell(15).getStringCellValue();
                    if(idLeyLong1 == 1) {
                        Object idCategoriaProfesionOject1 = funcionarioService.getIdCategoriaProfesionByCategoriaProfesionName(categoriaProfesion1);
                        if (!ObjectUtils.isEmpty(idCategoriaProfesionOject1) && idCategoriaProfesionOject1 != null) {
                            idCategoriaProfesionLong1 = Long.parseLong(idCategoriaProfesionOject1.toString());
                        }
                    } else if(idLeyLong1 == 2 || idLeyLong1 == 3) {
                        Object idCategoriaProfesionObject1 = funcionarioService.getIdCategoriaProfesionByCategoriaProfesionNameLeyHonorarioAndCodigoTrabajo(categoriaProfesion1);
                        if (!ObjectUtils.isEmpty(idCategoriaProfesionObject1) && idCategoriaProfesionObject1 != null) {
                            idCategoriaProfesionLong1 = Long.parseLong(idCategoriaProfesionObject1.toString());
                        }
                    }
                }

                String nivelCarrera1 = null;
                Long idNivelCarreraLong1 = 0L;
                if(row1.getCell(16).getCellType() == CellType.STRING) {
                    nivelCarrera1 = row1.getCell(16).getStringCellValue();
                    if(nivelCarrera1.equals("N/A")) {
                        nivelCarrera1 = "0";
                    }
                    if(idLeyLong1 == 1) {
                        Object idNivelCarreraObject1 = funcionarioService.getIdNivelCarreraByNivelCarreraNameLey19378(nivelCarrera1);
                        if (!ObjectUtils.isEmpty(idNivelCarreraObject1) && idNivelCarreraObject1 != null) {
                            idNivelCarreraLong1 = Long.parseLong(idNivelCarreraObject1.toString());
                        }
                    } else if(idLeyLong1 == 2 || idLeyLong1 == 3) {
                        Object idNivelCarreraObject1 = funcionarioService.getIdNivelCarreraByNivelCarreraNameLeyHonorarioAndCodigoTrabajo(nivelCarrera1);
                        if (!ObjectUtils.isEmpty(idNivelCarreraObject1) && idNivelCarreraObject1 != null) {
                            idNivelCarreraLong1 = Long.parseLong(idNivelCarreraObject1.toString());
                        }
                    }
                }

                String profesion1 = null;
                Long idProfesionLong1 = 0L;
                if(row1.getCell(17).getCellType() == CellType.STRING) {
                    profesion1 = row1.getCell(17).getStringCellValue();
                    if(idLeyLong1 == 1) {
                        Object idProfesionObject1 = funcionarioService.getIdProfesionByProfesionNameAndIdCategoriaProfesionLong(profesion1, idCategoriaProfesionLong1);
                        if (!ObjectUtils.isEmpty(idProfesionObject1) && idProfesionObject1 != null) {
                            idProfesionLong1 = Long.parseLong(idProfesionObject1.toString());
                        }
                    } else if(idLeyLong1 == 2 || idLeyLong1 == 3) {
                        Object idProfesionObject1 = funcionarioService.getIdProfesionByProfesionNameAndIdCategoriaProfesionLongLeyHonorarioAndCodigoTrabajo(profesion1, idCategoriaProfesionLong1);
                        if (!ObjectUtils.isEmpty(idProfesionObject1) && idProfesionObject1 != null) {
                            idProfesionLong1 = Long.parseLong(idProfesionObject1.toString());
                        }
                    }
                }

                String especialidad1 = null;
                Long idEspecialidadLong1 = 0L;
                if(row1.getCell(18).getCellType() == CellType.STRING) {
                    especialidad1 = row1.getCell(18).getStringCellValue();
                    Object idEspecialidadObject1 = funcionarioService.getIdEspecialidadByEspecialidadNameAndIdProfesionLong(especialidad1, idProfesionLong1);
                    if (!ObjectUtils.isEmpty(idEspecialidadObject1) && idEspecialidadObject1 != null) {
                        idEspecialidadLong1 = Long.parseLong(idEspecialidadObject1.toString());
                    }
                }

                String cargo1 = null;
                Long idCargoLong1 = 0L;
                if(row1.getCell(19).getCellType() == CellType.STRING) {
                    cargo1 = row1.getCell(19).getStringCellValue();
                    Object idCargoObject1 = funcionarioService.getIdCargoByCargoName(cargo1);
                    if (!ObjectUtils.isEmpty(idCargoObject1) && idCargoObject1 != null) {
                        idCargoLong1 = Long.parseLong(idCargoObject1.toString());
                    }
                }

                String asignacionChofer1 = null;
                Long idAsignacionChoferLong1 = 0L;
                if(row1.getCell(20).getCellType() == CellType.STRING) {
                    asignacionChofer1 = row1.getCell(20).getStringCellValue();
                    Object idAsignacionChoferObject1 = funcionarioService.getIdAsignacionChoferByAsignacionChoferNameAndIdCargoLong(asignacionChofer1, idCargoLong1);
                    if (!ObjectUtils.isEmpty(idAsignacionChoferObject1) && idAsignacionChoferObject1 != null) {
                        idAsignacionChoferLong1 = Long.parseLong(idAsignacionChoferObject1.toString());
                    }
                }

                Integer jornadaLaboral1 = 0;
                Long jornadaLaboralLong1 = 0L;
                Long countJornadaLaboraLong1 = 0L;
                Long totalJornadaLong1 = 0L;
                if(row1.getCell(21).getCellType() == CellType.NUMERIC) {
                    jornadaLaboral1 = (int) row1.getCell(21).getNumericCellValue();
                    jornadaLaboralLong1 = Long.valueOf(jornadaLaboral1);
                    Object countJornadaLaboraObject1 = funcionarioService.getCountJornadaLaboralByRutFuncionario(rutCompleto1);
                    if (!ObjectUtils.isEmpty(countJornadaLaboraObject1) && countJornadaLaboraObject1 != null) {
                        countJornadaLaboraLong1 = Long.parseLong(countJornadaLaboraObject1.toString());
                    }
                    totalJornadaLong1 = countJornadaLaboraLong1 + jornadaLaboralLong1;
                }

                String aniosServicio1 = null;
                Integer aniosServicioInteger1 = 0;
                if(row1.getCell(22).getCellType() == CellType.STRING) {
                    aniosServicio1 = row1.getCell(22).getStringCellValue();
                    aniosServicioInteger1 = Integer.parseInt(aniosServicio1);
                }

                String fechaIngresoString1 = "";
                Date fechaIngresoDate1 = null;
                String fechaIngresoStringFormat1 = "";
                if(row1.getCell(23).getCellType() == CellType.STRING) {
                    fechaIngresoString1 = row1.getCell(23).getStringCellValue();    // 1957-02-27
                    try {
                        fechaIngresoDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaIngresoString1); // Wed Feb 27 00:00:00 CLT 1957

                        String pattern1 = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
                        fechaIngresoStringFormat1 = simpleDateFormat1.format(fechaIngresoDate1);
                    } catch (Exception e) {
                        fechaIngresoString1 = null;
                        fechaIngresoDate1 = null;
                        fechaIngresoStringFormat1 = null;
                    }
                }

                String bienios1 = null;
                Long idBieniosLong1 = 0L;
                if(row1.getCell(24).getCellType() == CellType.STRING) {
                    bienios1 = row1.getCell(24).getStringCellValue();
                    Object idBieniosObject1 = funcionarioService.getIdBieniosByBieniosName(bienios1);
                    if (!ObjectUtils.isEmpty(idBieniosObject1) && idBieniosObject1 != null) {
                        idBieniosLong1 = Long.parseLong(idBieniosObject1.toString());
                    }
                }

                String prevision1 = null;
                Long idPrevisionLong1 = 0L;
                if(row1.getCell(25).getCellType() == CellType.STRING) {
                    prevision1 = row1.getCell(25).getStringCellValue();
                    Object idPrevisionObject1 = funcionarioService.getIdPrevisionByPrevisionName(prevision1);
                    if (!ObjectUtils.isEmpty(idPrevisionObject1) && idPrevisionObject1 != null) {
                        idPrevisionLong1 = Long.parseLong(idPrevisionObject1.toString());
                    }
                }

                String isapre1 = null;
                Long idIsapreLong1 = 0L;
                if(row1.getCell(26).getCellType() == CellType.STRING) {
                    isapre1 = row1.getCell(26).getStringCellValue();
                    Object idIsapreObject1 = funcionarioService.getIdIsapreByIsapreName(isapre1);
                    if (!ObjectUtils.isEmpty(idIsapreObject1) && idIsapreObject1 != null) {
                        idIsapreLong1 = Long.parseLong(idIsapreObject1.toString());
                    }
                }

                String sueldoBase1 = null;
                Long sueldoBaseLong1 = 0L;
                if(row1.getCell(27).getCellType() == CellType.STRING) {
                    sueldoBase1 = row1.getCell(27).getStringCellValue();
                    try {
                        sueldoBaseLong1 = Long.parseLong(sueldoBase1);
                    } catch (NumberFormatException e) {
                        sueldoBaseLong1 = null;
                    }
                }

                String totalHaberes1 = null;
                Long totalHaberesLong1 = 0L;
                if(row1.getCell(28).getCellType() == CellType.STRING) {
                    totalHaberes1 = row1.getCell(28).getStringCellValue();
                    try {
                        totalHaberesLong1 = Long.parseLong(totalHaberes1);
                    } catch (NumberFormatException e) {
                        totalHaberesLong1 = null;
                    }
                }

                String validado1 = null;
                boolean validadoBoolean1 = false;
                if(row1.getCell(29).getCellType() == CellType.STRING) {
                    validado1 = row1.getCell(29).getStringCellValue();
                    if(validado1.equals("Si")) {
                        validadoBoolean1 = true;
                    } else if(validado1.equals("No")) {
                        validadoBoolean1 = false;
                    } else {
                        validadoBoolean1 = false;
                    }
                }

                String revisado1 = null;
                boolean revisadoBoolean1 = false;
                if(row1.getCell(30).getCellType() == CellType.STRING) {
                    revisado1 = row1.getCell(30).getStringCellValue();
                    if(revisado1.equals("Si")) {
                        revisadoBoolean1 = true;
                    } else if(revisado1.equals("No")) {
                        revisadoBoolean1 = false;
                    } else {
                        revisadoBoolean1 = false;
                    }
                }

                System.out.print("Rows First:: [N°: " + numeroRegistro1 + "] " + " | Servicio Id: " + idServicioLong1 + " | Código Comuna: " + idComunaLong1);
                System.out.print(" | Cod Nuevo Establecimiento: " + idEstablecimientoInteger1 + " | Admin: " + idAdminSaludLong1 + " | Run Funcionario: " + rutCompleto1);
                System.out.print(" | Ley: " + idLeyLong1 + " | Tipo Contrato: " + idTipoContratoLong1 + " | Categoría: " + idCategoriaProfesionLong1 + " | Nivel Carrera: " + idNivelCarreraLong1);
                System.out.print(" | Profesión: " + idProfesionLong1 + " | Especialidad: " + idEspecialidadLong1 + " | Cargo: " + idCargoLong1 + " | Asig. Chofer: " + idAsignacionChoferLong1);
                System.out.print(" | Jornada: " + jornadaLaboral1 + " | Años de Serv.:" + aniosServicioInteger1 + " | Fecha Ingreso: " + fechaIngresoString1 + " | Bienios: " + idBieniosLong1);
                System.out.print(" | Previsión: " + idPrevisionLong1 + " | Isapre: " + idIsapreLong1 + " | Sueldo Base: " + sueldoBaseLong1 + " | Total Haberes: " + totalHaberesLong1);
                System.out.print(" | Validado: " + validadoBoolean1 + " | Revisado: " + revisadoBoolean1);
                System.out.println("[--################################################################################################################################################--|> :::");

                Iterator<Row> rowsSecond = sheet.iterator();
                rowsSecond.next();
                int x2 = 0;
                int jornadaLaboralExcel = 0;
                while (rowsSecond.hasNext()) {
                    Row row2 = rowsSecond.next();

                    Long numeroRegistro2 = null;
                    if(row2.getCell(0).getCellType() == CellType.NUMERIC) {
                        numeroRegistro2 = (long) row2.getCell(0).getNumericCellValue();
                    }

                    Long idServicioLong2 = null;
                    String servicioSalud2 = null;
                    if(row2.getCell(1).getCellType() == CellType.STRING) {
                        servicioSalud2 = row2.getCell(1).getStringCellValue();
                        List idServicioList2 = funcionarioService.getIdServicioByServicioName(servicioSalud2);
                        if (!idServicioList2.isEmpty() && idServicioList2 != null) {
                            Object idServicioObject2 = idServicioList2.get(0);
                            idServicioLong2 = Long.parseLong(idServicioObject2.toString());
                        }
                    }

                    Long idComunaLong2 = null;
                    String comuna2 = null;
                    if(row2.getCell(2).getCellType() == CellType.STRING) {
                        comuna2 = row2.getCell(2).getStringCellValue();
                        List idComunaList2 = funcionarioService.getIdComunaByComunaName(comuna2);
                        if (!idComunaList2.isEmpty() && idComunaList2 != null) {
                            Object idComunaObject2 = idComunaList2.get(0);
                            idComunaLong2 = Long.parseLong(idComunaObject2.toString());
                        }
                    }

                    Long idEstablecimientoLong2 = null;
                    int idEstablecimientoInteger2 = 0;
                    String establecimiento2 = null;
                    if(row2.getCell(3).getCellType() == CellType.STRING) {
                        establecimiento2 = row2.getCell(3).getStringCellValue();
                        try {
                            Object idEstablecimientoObject2 = funcionarioService.getIdEstablecimientoByEstablecimientoName(establecimiento2);
                            idEstablecimientoLong2 = Long.parseLong(idEstablecimientoObject2.toString());
                            idEstablecimientoInteger2 = Integer.parseInt(idEstablecimientoObject2.toString());
                        } catch (Exception ex) {
                            idEstablecimientoInteger2 = 0;
                            idEstablecimientoLong2 = null;
                        }
                    }

                    String adminSalud2 = null;
                    Long idAdminSaludLong2 = null;
                    if(row2.getCell(4).getCellType() == CellType.STRING) {
                        adminSalud2 = row2.getCell(4).getStringCellValue();
                        Object idAdminSaludObject2 = funcionarioService.getIdAdminSaludByAdminSaludName(adminSalud2);
                        if(!ObjectUtils.isEmpty(idAdminSaludObject2) && idAdminSaludObject2 != null) {
                            idAdminSaludLong2 = Long.parseLong(idAdminSaludObject2.toString());
                        }
                    }

                    String rut2 = null;
                    String dv2 = null;
                    String rutCompleto2 = null;
                    if(row2.getCell(5).getCellType() == CellType.STRING && row2.getCell(6).getCellType() == CellType.STRING) {
                        rut2 = row2.getCell(5).getStringCellValue();
                        dv2 = row2.getCell(6).getStringCellValue();
                        rutCompleto2 = rut2 + "-" + dv2;
                    }

                    String ley2 = null;
                    Long idLeyLong2 = null;
                    if(row2.getCell(13).getCellType() == CellType.STRING) {
                        ley2 = row2.getCell(13).getStringCellValue();
                        Object idLeyObject2 = funcionarioService.getIdLeyByLeyName(ley2);
                        if(!ObjectUtils.isEmpty(idLeyObject2) && idLeyObject2 != null) {
                            idLeyLong2 = Long.parseLong(idLeyObject2.toString());
                        }
                    }

                    String tipoContrato2 = null;
                    Long idTipoContratoLong2 = null;
                    if(row2.getCell(14).getCellType() == CellType.STRING) {
                        tipoContrato2 = row2.getCell(14).getStringCellValue();
                        if(idLeyLong2 != null) {
                            Object idTipoContratoObject2 = funcionarioService.getIdTipoContratoByTipoContratoNameAndIdLeyLong(tipoContrato2, idLeyLong2);
                            if(!ObjectUtils.isEmpty(idTipoContratoObject2) && idTipoContratoObject2 != null) {
                                idTipoContratoLong2 = Long.parseLong(idTipoContratoObject2.toString());
                            }
                        }
                    }

                    String categoriaProfesion2 = null;
                    Long idCategoriaProfesionLong2 = null;
                    if(row2.getCell(15).getCellType() == CellType.STRING) {
                        categoriaProfesion2 = row2.getCell(15).getStringCellValue();
                        if(idLeyLong2 != null && idLeyLong2 == 1) {
                            Object idCategoriaProfesionOject2 = funcionarioService.getIdCategoriaProfesionByCategoriaProfesionName(categoriaProfesion2);
                            if (!ObjectUtils.isEmpty(idCategoriaProfesionOject2) && idCategoriaProfesionOject2 != null) {
                                idCategoriaProfesionLong2 = Long.parseLong(idCategoriaProfesionOject2.toString());
                            }
                        } else if(idLeyLong2 != null && (idLeyLong2 == 2 || idLeyLong2 == 3)) {
                            Object idCategoriaProfesionObject2 = funcionarioService.getIdCategoriaProfesionByCategoriaProfesionNameLeyHonorarioAndCodigoTrabajo(categoriaProfesion2);
                            if (!ObjectUtils.isEmpty(idCategoriaProfesionObject2) && idCategoriaProfesionObject2 != null) {
                                idCategoriaProfesionLong2 = Long.parseLong(idCategoriaProfesionObject2.toString());
                            }
                        }
                    }

                    String nivelCarrera2 = null;
                    Long idNivelCarreraLong2 = null;
                    if(row2.getCell(16).getCellType() == CellType.STRING) {
                        nivelCarrera2 = row2.getCell(16).getStringCellValue();
                        if(nivelCarrera2.equals("N/A")) {
                            nivelCarrera2 = "0";
                        }
                        if(idLeyLong2 != null && idLeyLong2 == 1) {
                            Object idNivelCarreraObject2 = funcionarioService.getIdNivelCarreraByNivelCarreraNameLey19378(nivelCarrera2);
                            if (!ObjectUtils.isEmpty(idNivelCarreraObject2) && idNivelCarreraObject2 != null) {
                                idNivelCarreraLong2 = Long.parseLong(idNivelCarreraObject2.toString());
                            }
                        } else if(idLeyLong2 != null && (idLeyLong2 == 2 || idLeyLong2 == 3)) {
                            Object idNivelCarreraObject2 = funcionarioService.getIdNivelCarreraByNivelCarreraNameLeyHonorarioAndCodigoTrabajo(nivelCarrera2);
                            if (!ObjectUtils.isEmpty(idNivelCarreraObject2) && idNivelCarreraObject2 != null) {
                                idNivelCarreraLong2 = Long.parseLong(idNivelCarreraObject2.toString());
                            }
                        }
                    }

                    String profesion2 = null;
                    Long idProfesionLong2 = null;
                    if(row2.getCell(17).getCellType() == CellType.STRING) {
                        profesion2 = row2.getCell(17).getStringCellValue();
                        if(idLeyLong2 != null && idLeyLong2 == 1) {
                            Object idProfesionObject2 = funcionarioService.getIdProfesionByProfesionNameAndIdCategoriaProfesionLong(profesion2, idCategoriaProfesionLong2);
                            if (!ObjectUtils.isEmpty(idProfesionObject2) && idProfesionObject2 != null) {
                                idProfesionLong2 = Long.parseLong(idProfesionObject2.toString());
                            }
                        } else if(idLeyLong2 != null && (idLeyLong2 == 2 || idLeyLong2 == 3)) {
                            Object idProfesionObject2 = funcionarioService.getIdProfesionByProfesionNameAndIdCategoriaProfesionLongLeyHonorarioAndCodigoTrabajo(profesion2, idCategoriaProfesionLong2);
                            if (!ObjectUtils.isEmpty(idProfesionObject2) && idProfesionObject2 != null) {
                                idProfesionLong2 = Long.parseLong(idProfesionObject2.toString());
                            }
                        }
                    }

                    String especialidad2 = null;
                    Long idEspecialidadLong2 = null;
                    if(row2.getCell(18).getCellType() == CellType.STRING) {
                        especialidad2 = row2.getCell(18).getStringCellValue();
                        Object idEspecialidadObject2 = funcionarioService.getIdEspecialidadByEspecialidadNameAndIdProfesionLong(especialidad2, idProfesionLong2);
                        if (!ObjectUtils.isEmpty(idEspecialidadObject2) && idEspecialidadObject2 != null) {
                            idEspecialidadLong2 = Long.parseLong(idEspecialidadObject2.toString());
                        }
                    }

                    String cargo2 = null;
                    Long idCargoLong2 = null;
                    if(row2.getCell(19).getCellType() == CellType.STRING) {
                        cargo2 = row2.getCell(19).getStringCellValue();
                        Object idCargoObject2 = funcionarioService.getIdCargoByCargoName(cargo2);
                        if (!ObjectUtils.isEmpty(idCargoObject2) && idCargoObject2 != null) {
                            idCargoLong2 = Long.parseLong(idCargoObject2.toString());
                        }
                    }

                    String asignacionChofer2 = null;
                    Long idAsignacionChoferLong2 = null;
                    if(row2.getCell(20).getCellType() == CellType.STRING) {
                        asignacionChofer2 = row2.getCell(20).getStringCellValue();
                        Object idAsignacionChoferObject2 = funcionarioService.getIdAsignacionChoferByAsignacionChoferNameAndIdCargoLong(asignacionChofer2, idCargoLong2);
                        if (!ObjectUtils.isEmpty(idAsignacionChoferObject2) && idAsignacionChoferObject2 != null) {
                            idAsignacionChoferLong2 = Long.parseLong(idAsignacionChoferObject2.toString());
                        }
                    }

                    Integer jornadaLaboral2 = null;
                    Long jornadaLaboralLong2 = null;
                    Long countJornadaLaboraLong2 = 0L;
                    Long totalJornadaLong2 = 0L;
                    if(row2.getCell(21).getCellType() == CellType.NUMERIC) {
                        jornadaLaboral2 = (int) row2.getCell(21).getNumericCellValue();
                        jornadaLaboralLong2 = Long.valueOf(jornadaLaboral2);
                        Object countJornadaLaboraObject2 = funcionarioService.getCountJornadaLaboralByRutFuncionario(rutCompleto2);
                        if (!ObjectUtils.isEmpty(countJornadaLaboraObject2) && countJornadaLaboraObject2 != null) {
                            countJornadaLaboraLong2 = Long.parseLong(countJornadaLaboraObject2.toString());
                        }
                        totalJornadaLong2 = countJornadaLaboraLong2 + jornadaLaboralLong2;
                    }

                    String aniosServicio2 = null;
                    Integer aniosServicioInteger2 = null;
                    if(row2.getCell(22).getCellType() == CellType.STRING) {
                        aniosServicio2 = row2.getCell(22).getStringCellValue();
                        aniosServicioInteger2 = Integer.parseInt(aniosServicio2);
                    }

                    String fechaIngresoString2 = null;
                    Date fechaIngresoDate2 = null;
                    String fechaIngresoStringFormat2 = null;
                    if(row2.getCell(23).getCellType() == CellType.STRING) {
                        fechaIngresoString2 = row2.getCell(23).getStringCellValue();    // 1957-02-27
                        try {
                            fechaIngresoDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaIngresoString2); // Wed Feb 27 00:00:00 CLT 1957

                            String pattern2 = "yyyy-MM-dd";
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
                            fechaIngresoStringFormat2 = simpleDateFormat2.format(fechaIngresoDate2);
                        } catch (Exception e) {
                            fechaIngresoString2 = null;
                            fechaIngresoDate2 = null;
                            fechaIngresoStringFormat2 = null;
                        }
                    }

                    String bienios2 = null;
                    Long idBieniosLong2 = null;
                    if(row2.getCell(24).getCellType() == CellType.STRING) {
                        bienios2 = row2.getCell(24).getStringCellValue();
                        Object idBieniosObject2 = funcionarioService.getIdBieniosByBieniosName(bienios2);
                        if (!ObjectUtils.isEmpty(idBieniosObject2) && idBieniosObject2 != null) {
                            idBieniosLong2 = Long.parseLong(idBieniosObject2.toString());
                        }
                    }

                    String prevision2 = null;
                    Long idPrevisionLong2 = null;
                    if(row2.getCell(25).getCellType() == CellType.STRING) {
                        prevision2 = row2.getCell(25).getStringCellValue();
                        Object idPrevisionObject2 = funcionarioService.getIdPrevisionByPrevisionName(prevision2);
                        if (!ObjectUtils.isEmpty(idPrevisionObject2) && idPrevisionObject2 != null) {
                            idPrevisionLong2 = Long.parseLong(idPrevisionObject2.toString());
                        }
                    }

                    String isapre2 = null;
                    Long idIsapreLong2 = null;
                    if(row2.getCell(26).getCellType() == CellType.STRING) {
                        isapre2 = row2.getCell(26).getStringCellValue();
                        Object idIsapreObject2 = funcionarioService.getIdIsapreByIsapreName(isapre2);
                        if (!ObjectUtils.isEmpty(idIsapreObject2) && idIsapreObject2 != null) {
                            idIsapreLong2 = Long.parseLong(idIsapreObject2.toString());
                        }
                    }

                    String sueldoBase2 = null;
                    Long sueldoBaseLong2 = null;
                    if(row2.getCell(27).getCellType() == CellType.STRING) {
                        sueldoBase2 = row2.getCell(27).getStringCellValue();
                        try {
                            sueldoBaseLong2 = Long.parseLong(sueldoBase2);
                        } catch (NumberFormatException e) {
                            sueldoBaseLong2 = null;
                        }
                    }

                    String totalHaberes2 = null;
                    Long totalHaberesLong2 = null;
                    if(row2.getCell(28).getCellType() == CellType.STRING) {
                        totalHaberes2 = row2.getCell(28).getStringCellValue();
                        try {
                            totalHaberesLong2 = Long.parseLong(totalHaberes2);
                        } catch (NumberFormatException e) {
                            totalHaberesLong2 = null;
                        }
                    }

                    String validado2 = null;
                    boolean validadoBoolean2 = false;
                    if(row2.getCell(29).getCellType() == CellType.STRING) {
                        validado2 = row2.getCell(29).getStringCellValue();
                        if(validado2.equals("Si")) {
                            validadoBoolean2 = true;
                        } else if(validado2.equals("No")) {
                            validadoBoolean2 = false;
                        } else {
                            validadoBoolean2 = false;
                        }
                    }

                    String revisado2 = null;
                    boolean revisadoBoolean2 = false;
                    if(row2.getCell(30).getCellType() == CellType.STRING) {
                        revisado2 = row2.getCell(30).getStringCellValue();
                        if(revisado2.equals("Si")) {
                            revisadoBoolean2 = true;
                        } else if(revisado2.equals("No")) {
                            revisadoBoolean2 = false;
                        } else {
                            revisadoBoolean2 = false;
                        }
                    }

                    System.out.print(" ----> Rows Second:: [N°: " + numeroRegistro2 + "] " + " | Servicio Id: " + idServicioLong2 + " | Código Comuna: " + idComunaLong2);
                    System.out.print(" | Cod Nuevo Establecimiento: " + idEstablecimientoInteger2 + " | Admin: " + idAdminSaludLong2 + " | Run Funcionario: " + rutCompleto2);
                    System.out.print(" | Ley: " + idLeyLong2 + " | Tipo Contrato: " + idTipoContratoLong2 + " | Categoría: " + idCategoriaProfesionLong2 + " | Nivel Carrera: " + idNivelCarreraLong2);
                    System.out.print(" | Profesión: " + idProfesionLong2 + " | Especialidad: " + idEspecialidadLong2 + " | Cargo: " + idCargoLong2 + " | Asig. Chofer: " + idAsignacionChoferLong2);
                    System.out.print(" | Jornada: " + jornadaLaboral2 + " | Años de Serv.:" + aniosServicioInteger2 + " | Fecha Ingreso: " + fechaIngresoString2 + " | Bienios: " + idBieniosLong2);
                    System.out.print(" | Previsión: " + idPrevisionLong2 + " | Isapre: " + idIsapreLong2 + " | Sueldo Base: " + sueldoBaseLong2 + " | Total Haberes: " + totalHaberesLong2);
                    System.out.print(" | Validado: " + validadoBoolean2 + " | Revisado: " + revisadoBoolean2);
                    System.out.println();

                    if(idServicioLong1.equals(idServicioLong2) && idComunaLong1.equals(idComunaLong2) && idEstablecimientoInteger1 == idEstablecimientoInteger2 && idAdminSaludLong1.equals(idAdminSaludLong2) &&
                            rutCompleto1.equals(rutCompleto2) && idLeyLong1.equals(idLeyLong2) && idTipoContratoLong1.equals(idTipoContratoLong2) && idCategoriaProfesionLong1.equals(idCategoriaProfesionLong2) &&
                            idNivelCarreraLong1.equals(idNivelCarreraLong2) && idProfesionLong1.equals(idProfesionLong2) && idEspecialidadLong1.equals(idEspecialidadLong2) && idCargoLong1.equals(idCargoLong2) &&
                            idAsignacionChoferLong1.equals(idAsignacionChoferLong2) && jornadaLaboral1.equals(jornadaLaboral2) && aniosServicioInteger1.equals(aniosServicioInteger2) && fechaIngresoString1.equals(fechaIngresoString2) &&
                            idBieniosLong1.equals(idBieniosLong2) && idPrevisionLong1.equals(idPrevisionLong2) && idIsapreLong1.equals(idIsapreLong2) && sueldoBaseLong1.equals(sueldoBaseLong2) && totalHaberesLong1.equals(totalHaberesLong2) &&
                            validadoBoolean1 == validadoBoolean2 && revisadoBoolean1 == revisadoBoolean2
                    ) {
                        x2++;
                        System.out.println("----------#### Row First [" + numeroRegistro1 + "] es identica a Row Second [" + numeroRegistro2 + " [ x2 = " + x2 + " ] ####------------");
                    }

                    if(rutCompleto1.equals(rutCompleto2) && idLeyLong2 != null && (idLeyLong2 == 1 || idLeyLong2 == 3)) {
                        jornadaLaboralExcel = jornadaLaboralExcel + jornadaLaboral2;
                        System.out.println("En planilla Excel, la sumatoria de jornada Laboral con ley '" + ley2 + " (" + idLeyLong2 + ")' para el funcionario con run " + rutCompleto2 + " suma un total de " + jornadaLaboralExcel + ", en linea N° " + numeroRegistro2);
                    }

                }   // while --> rowsSecond

                if(x2 > 1) {
                    errorFlag = true;
                    System.out.println("En planilla Excel existe un registro duplicado correspondiente al funcionario con Run " + rutCompleto1 + ", en linea N° " + numeroRegistro1 + " [ con x = " + x2 + " ]");
                    errorMessages.add("En planilla Excel existe un registro duplicado correspondiente al funcionario con Run " + rutCompleto1 + ", en linea N° " + numeroRegistro1);
                }

                if(idLeyLong1 != null && (idLeyLong1 == 1 || idLeyLong1 == 3) && (jornadaLaboralExcel > 44 || jornadaLaboralExcel < 1)) {
                    errorFlag = true;
                    rutCompleto1 = !rutCompleto1.equals("") ? rutCompleto1 : "con formato inválido";
                    System.out.println("En planilla Excel, la Jornada Laboral con 'Ley 19.378' y/o 'Código del Trabajo' para el funcionario con Run " + rutCompleto1 + " suma un total de " + jornadaLaboralExcel + " horas, no puede ser mayor a 44 horas ni menor a 1 hora, en linea N° " + numeroRegistro1);
                    errorMessages.add("En planilla Excel, la Jornada Laboral con 'Ley 19.378' y/o 'Código del Trabajo' para el funcionario con Run " + rutCompleto1 + " suma un total de " + jornadaLaboralExcel + " horas, no puede ser mayor a 44 horas ni menor a 1 hora. O bien puede ser que el Run tenga fomato inválido, en linea N° " + numeroRegistro1);
                }

            }   // while --> rowsFirst
            */
            /* ########################################################### [ FIN: BUCLE ANIDADO: VALIDACIÓN JORNADA LABORA Y REGISTROS DUPLICADOS] ########################################################### */

            if (errorFlag) {
                flash.addFlashAttribute("errorMessages", errorMessages);
                return "redirect:/carga-masiva/index";
            } else {
                // System.out.println(funcionarioArrayList);
                boolean ifExistFuncionarioRut = true;
                for(Funcionario fun : funcionarioArrayList) {
                    // System.out.println( fun );
                    ifExistFuncionarioRut = funcionarioService.ifExistFuncionarioRut(fun.getRun());
                    if(!ifExistFuncionarioRut) {
                        funcionarioService.save(fun);
                    }
                }

                for(Contrato con : contratoArrayList) {
                    // System.out.println(con);
                    Object idFuncionarioObject = funcionarioService.getIdFuncionarioByRutFuncionario(con.getRutFuncionario());
                    Long idFuncionarioLong = Long.parseLong(idFuncionarioObject.toString());
                    Funcionario funcionarioEntity = new Funcionario();
                    funcionarioEntity.setId(idFuncionarioLong);
                    con.setFuncionario(funcionarioEntity);
                    funcionarioService.saveContratoCustom(con);
                }

                flash.addFlashAttribute("success", "Archivo cargado correctamente");
            }

            logger.info("Nombre del Archivo: " + file.getOriginalFilename());
            logger.info("Extención del archivo: " + extension);
            logger.info("Extención del archivo V2: " + extensionV2);
            logger.info("Sheet: " + sheet.getSheetName());
            logger.info("Error Flag: " + errorFlag);
            logger.info("Rows Number: " + rowsNumber);
        } else {
            //
            logger.info("El archivo a cargar debe ser de tipo Excel con extención xlsx");
            flash.addFlashAttribute("error", "El archivo a cargar debe ser de tipo Excel con extención xlsx");
        }

        return "redirect:/carga-masiva/index";
    }

}
