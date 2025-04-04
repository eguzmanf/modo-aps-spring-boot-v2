package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.models.dao.comuna.IComunaDao;
import cl.divap.modoaps.app.models.dao.establecimiento.IEstablecimientoDao;
import cl.divap.modoaps.app.models.dao.establecimiento.IJpaRepositoryEstablecimientoDao;
import cl.divap.modoaps.app.models.entity.*;
import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/carga-masiva-establecimientos")
public class CargaMasivaEstablecimientosController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IComunaDao comunaDao;

    @Autowired
    private IJpaRepositoryEstablecimientoDao establecimientoDaoJpaRepo;

    @GetMapping(value={"", "/", "/index", "carga-archivo"})
    public String index(Model model) {
        Establecimiento establecimiento = new Establecimiento();
        model.addAttribute("titulo", "Ingreso Dotación - Carga masiva de Establecimientos");
        model.addAttribute("establecimiento", establecimiento);
        return "carga-masiva-establecimientos/index";
    }

    @PostMapping(value="carga-archivo-establecimientos")
    public String cargaArchivo(Establecimiento establecimiento, RedirectAttributes flash, Model model) throws IOException, ParseException {
        //
        List<Establecimiento> establecimientosArrayList = new ArrayList<Establecimiento>();

        MultipartFile file = establecimiento.getCargaMasivaEstablecimientos();

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

                String acompanamiento = null;
                if(row.getCell(1).getCellType() == CellType.STRING) {
                    acompanamiento =  row.getCell(1).getStringCellValue();
                    if(acompanamiento.equals("NULL")) {
                        establecimiento.setAcompanamiento(null);
                    } else {
                        establecimiento.setAcompanamiento(acompanamiento);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("El acompanamiento no es válido, debe ser NULL, en linea N° " + numeroRegistro);
                }

                String dir = null;
                if(row.getCell(2).getCellType() == CellType.STRING) {
                    dir =  row.getCell(2).getStringCellValue();
                    if(dir.equals("NULL")) {
                        establecimiento.setDIR(null);
                    } else {
                        establecimiento.setDIR(dir);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("El dir no es válido, debe ser NULL, en linea N° " + numeroRegistro);
                }

                String nivelAtencion = null;
                if(row.getCell(3).getCellType() == CellType.STRING) {
                    nivelAtencion = row.getCell(3).getStringCellValue();
                    if(nivelAtencion.equals("NULL")) {
                        establecimiento.setNivel_Atencion(null);
                    } else {
                        establecimiento.setNivel_Atencion(nivelAtencion);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("El nivel atencion no es válido, debe ser NULL, Primario o Secundario, en linea N° " + numeroRegistro);
                }
                
                String codigoAntiguo = null;
                if(row.getCell(4).getCellType() == CellType.STRING) {
                    codigoAntiguo = row.getCell(4).getStringCellValue();
                    if(codigoAntiguo.equals("NULL")) {
                        establecimiento.setCodigoAntiguo(null);
                    } else {
                        establecimiento.setCodigoAntiguo(codigoAntiguo);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("El codigo antiguo no es válido, debe ser NULL o un valor string, en linea N° " + numeroRegistro);
                }

                int codigoNuevoInt = 0;
                if(row.getCell(5).getCellType() == CellType.NUMERIC) {
                    codigoNuevoInt = (int) row.getCell(5).getNumericCellValue();
                    establecimiento.setCodigoNuevo(codigoNuevoInt);
                } else {
                    errorFlag = true;
                    errorMessages.add("El codigo nuevo '" + codigoNuevoInt + "' no es válido, en linea N° " + numeroRegistro);
                }

                // Columna codigo comuna: 26
                String comunaMayusc = null;
                Long codigoComunaLong = null;
                if(row.getCell(6).getCellType() == CellType.STRING) {
                    comunaMayusc = row.getCell(6).getStringCellValue();
                    if(comunaMayusc.equals("NULL")) {
                        establecimiento.setComunaMayusc(null);
                    } else {
                        int codigoComuna = (int) row.getCell(26).getNumericCellValue();
                        codigoComunaLong = Long.parseLong(Integer.toString(codigoComuna));
                        Comuna comunaEntity = comunaDao.findByCodigoComuna(codigoComunaLong);
                        establecimiento.setComuna(comunaEntity);
                        comunaMayusc = comunaEntity.getComuna().toUpperCase();
                        establecimiento.setComunaMayusc(comunaMayusc);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La comunaMayusc '" + comunaMayusc + "', no es válido, debe ser NULL o string, en linea N° " + numeroRegistro);
                }

                // Columna codigo comuna: 26
                String comunaNombre = null;
                Long codigoComunaNombreLong = null;
                if(row.getCell(7).getCellType() == CellType.STRING) {
                    int codigoComunaNombre = (int) row.getCell(26).getNumericCellValue();
                    codigoComunaNombreLong = Long.parseLong(Integer.toString(codigoComunaNombre));
                    Comuna comunaNombreEntity = comunaDao.findByCodigoComuna(codigoComunaNombreLong);
                    establecimiento.setComuna(comunaNombreEntity);
                    comunaNombre = comunaNombreEntity.getComuna();
                    establecimiento.setComunaNombre(comunaNombre);
                }

                String dependencia = null;
                if(row.getCell(8).getCellType() == CellType.STRING) {
                    dependencia = row.getCell(8).getStringCellValue();
                    if(dependencia.equals("NULL")) {
                        establecimiento.setDependencia(null);
                    } else {
                        establecimiento.setDependencia(dependencia);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La dependencia no es válida, debe ser NULL, Municipal o Servicio, en linea N° " + numeroRegistro);
                }

                int elimidInt = 0;
                if(row.getCell(9).getCellType() == CellType.NUMERIC) {
                    elimidInt = (int) row.getCell(9).getNumericCellValue();
                    if(elimidInt == 0 || elimidInt == 1) {
                        establecimiento.setElimID(elimidInt);
                    } else {
                        errorFlag = true;
                        errorMessages.add("El elimid '" + elimidInt + "', no es válido, debe ser 0 o 1, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("El elimid '" + elimidInt + "', no es válido, debe ser 0 o 1, en linea N° " + numeroRegistro);
                }

                String establecimientoNombre = null;
                if(row.getCell(10).getCellType() == CellType.STRING) {
                    establecimientoNombre = row.getCell(10).getStringCellValue();
                    establecimiento.setEstablecimientoNombre(establecimientoNombre);
                } else {
                    errorFlag = true;
                    errorMessages.add("El establecimiento '" + establecimientoNombre + "', no es válido, en linea N° " + numeroRegistro);
                }

                String iaaps = null;
                if(row.getCell(11).getCellType() == CellType.STRING) {
                    iaaps = row.getCell(11).getStringCellValue();
                    if(iaaps.equals("NULL")) {
                        establecimiento.setIaaps(null);
                    } else {
                        establecimiento.setIaaps(iaaps);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La iaaps no es válido, debe ser NULL, SI o NO, en linea N° " + numeroRegistro);
                }

                String idComunaTexto = null;
                if(row.getCell(12).getCellType() == CellType.STRING) {
                    idComunaTexto = row.getCell(12).getStringCellValue();
                    if(idComunaTexto.equals("NULL")) {
                        establecimiento.setIdComunaTexto(null);
                    } else {
                        establecimiento.setIdComunaTexto(idComunaTexto);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La idComunaTexto '" + idComunaTexto + "', no es válido, debe ser NULL, o un valor numerico string, en linea N° " + numeroRegistro);
                }

                int idDependencia = 0;
                String idDependenciaString = null;
                if(row.getCell(13).getCellType() == CellType.STRING) {
                    idDependenciaString = row.getCell(13).getStringCellValue();
                    if(idDependenciaString.equals("NULL")) {
                        establecimiento.setIdDependencia(null);
                    } else {
                        errorFlag = true;
                        errorMessages.add("La idDependenciaString '" + idDependenciaString + "', no es válido, debe ser NULL, en linea N° " + numeroRegistro);
                    }
                } else if(row.getCell(13).getCellType() == CellType.NUMERIC) {
                    idDependencia = (int) row.getCell(13).getNumericCellValue();
                    establecimiento.setIdDependencia(idDependencia);
                } else {
                    errorFlag = true;
                    errorMessages.add("El idDependencia '" + idDependencia + "' no es válido, en linea N° " + numeroRegistro);
                }

                int idMacroZonaInt = 0;
                String idMacroZonaString = null;
                if(row.getCell(14).getCellType() == CellType.STRING) {
                    idMacroZonaString = row.getCell(14).getStringCellValue();
                    if(idMacroZonaString.equals("NULL")) {
                        establecimiento.setIdMacroZona(null);
                    } else {
                        errorFlag = true;
                        errorMessages.add("La idMacroZonaString '" + idMacroZonaString + "', no es válido, debe ser NULL, en linea N° " + numeroRegistro);
                    }
                } else if(row.getCell(14).getCellType() == CellType.NUMERIC) {
                    idMacroZonaInt = (int) row.getCell(14).getNumericCellValue();
                    establecimiento.setIdMacroZona(idMacroZonaInt);
                } else {
                    errorFlag = true;
                    errorMessages.add("El idMacroZona '" + idMacroZonaInt + "' no es válido, en linea N° " + numeroRegistro);
                }

                int idOrdenServicioInt = 0;
                if(row.getCell(15).getCellType() == CellType.NUMERIC) {
                    idOrdenServicioInt = (int) row.getCell(15).getNumericCellValue();
                    establecimiento.setIdOrdenServicio(idOrdenServicioInt);
                } else {
                    errorFlag = true;
                    errorMessages.add("El idOrdenServicioInt '" + idOrdenServicioInt + "' no es válido, en linea N° " + numeroRegistro);
                }

                int idServicioIdComunaInt = 0;
                if(row.getCell(16).getCellType() == CellType.NUMERIC) {
                    idServicioIdComunaInt = (int) row.getCell(16).getNumericCellValue();
                    establecimiento.setIdServicioIdComuna(idServicioIdComunaInt);
                } else {
                    errorFlag = true;
                    errorMessages.add("El idServicioIdComuna '" + idServicioIdComunaInt + "' no es válido, en linea N° " + numeroRegistro);
                }

                String idTipo = null;
                if(row.getCell(17).getCellType() == CellType.STRING) {
                    idTipo = row.getCell(17).getStringCellValue();
                    if(idTipo.equals("NULL")) {
                        establecimiento.setIdTipo(null);
                    } else {
                        establecimiento.setIdTipo(idTipo);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La idTipo no es válido, debe ser NULL, o un valor string, en linea N° " + numeroRegistro);
                }

                String idTipoEst = null;
                if(row.getCell(18).getCellType() == CellType.STRING) {
                    idTipoEst = row.getCell(18).getStringCellValue();
                    if(idTipoEst.equals("NULL")) {
                        establecimiento.setIdTipoEst(null);
                    } else {
                        establecimiento.setIdTipoEst(idTipoEst);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La idTipoEst no es válido, debe ser NULL, o un valor string, en linea N° " + numeroRegistro);
                }

                String macroZona = null;
                if(row.getCell(19).getCellType() == CellType.STRING) {
                    macroZona = row.getCell(19).getStringCellValue();
                    if(macroZona.equals("NULL")) {
                        establecimiento.setMacroZona(null);
                    } else {
                        establecimiento.setMacroZona(macroZona);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La macroZona '" + macroZona + "', no es válido, debe ser NULL, o un valor string, en linea N° " + numeroRegistro);
                }

                String regionAlias = null;
                if(row.getCell(20).getCellType() == CellType.STRING) {
                    regionAlias = row.getCell(20).getStringCellValue();
                    if(regionAlias.equals("NULL")) {
                        establecimiento.setRegionAlias(null);
                    } else {
                        establecimiento.setRegionAlias(regionAlias);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La regionAlias '" + regionAlias + "', no es válido, debe ser NULL, o un valor string, en linea N° " + numeroRegistro);
                }

                String regionCodigo = null;
                if(row.getCell(21).getCellType() == CellType.STRING) {
                    regionCodigo = row.getCell(21).getStringCellValue();
                    if(regionCodigo.equals("NULL")) {
                        establecimiento.setRegionCodigo(null);
                    } else {
                        establecimiento.setRegionCodigo(regionCodigo);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La regionCodigo '" + regionCodigo + "', no es válido, debe ser NULL, o un valor string, en linea N° " + numeroRegistro);
                }

                String regionNombre = null;
                if(row.getCell(22).getCellType() == CellType.STRING) {
                    regionNombre = row.getCell(22).getStringCellValue();
                    if(regionNombre.equals("NULL")) {
                        establecimiento.setRegionNombre(null);
                    } else {
                        establecimiento.setRegionNombre(regionNombre);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La regionNombre '" + regionNombre + "', no es válido, debe ser NULL, o un valor string, en linea N° " + numeroRegistro);
                }

                int regionOrdenInt = 0;
                String regionOrdenString = null;
                if(row.getCell(23).getCellType() == CellType.STRING) {
                    regionOrdenString = row.getCell(23).getStringCellValue();
                    if(regionOrdenString.equals("NULL")) {
                        establecimiento.setRegionOrden(null);
                    } else {
                        errorFlag = true;
                        errorMessages.add("La regionOrdenString '" + regionOrdenString + "', no es válido, debe ser NULL, en linea N° " + numeroRegistro);
                    }
                } else if(row.getCell(23).getCellType() == CellType.NUMERIC) {
                    regionOrdenInt = (int) row.getCell(23).getNumericCellValue();
                    establecimiento.setRegionOrden(regionOrdenInt);
                } else {
                    errorFlag = true;
                    errorMessages.add("El regionOrdenInt '" + regionOrdenInt + "' no es válido, en linea N° " + numeroRegistro);
                }

                String servicioNombre = null;
                if(row.getCell(24).getCellType() == CellType.STRING) {
                    servicioNombre = row.getCell(24).getStringCellValue();
                    if(servicioNombre.equals("NULL")) {
                        establecimiento.setServicioNombre(null);
                    } else {
                        establecimiento.setServicioNombre(servicioNombre);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La servicioNombre '" + servicioNombre + "', no es válido, debe ser NULL, o un valor string, en linea N° " + numeroRegistro);
                }

                String tipoEstablecimiento = null;
                if(row.getCell(25).getCellType() == CellType.STRING) {
                    tipoEstablecimiento = row.getCell(25).getStringCellValue();
                    if(tipoEstablecimiento.equals("NULL")) {
                        establecimiento.setTipoEstablecimiento(null);
                    } else {
                        establecimiento.setTipoEstablecimiento(tipoEstablecimiento);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("La tipoEstablecimiento no es válido, debe ser NULL, o un valor string, en linea N° " + numeroRegistro);
                }

                long codigoComuna = 0L;
                if(row.getCell(26).getCellType() == CellType.NUMERIC) {
                    codigoComuna = (long) row.getCell(26).getNumericCellValue();
                    Comuna comuna = new Comuna();
                    comuna.setCodigoComuna(codigoComuna);
                    establecimiento.setComuna(comuna);
                } else {
                    errorFlag = true;
                    errorMessages.add("El codigoComuna '" + codigoComuna + "' no es válido, en linea N° " + numeroRegistro);
                }

                long regionId = 0L;
                if(row.getCell(27).getCellType() == CellType.NUMERIC) {
                    regionId = (long) row.getCell(27).getNumericCellValue();
                    Region region = new Region();
                    region.setId(regionId);
                    establecimiento.setRegion(region);
                } else {
                    errorFlag = true;
                    errorMessages.add("El regionId '" + regionId + "' no es válido, en linea N° " + numeroRegistro);
                }

                long servicioId = 0L;
                if(row.getCell(28).getCellType() == CellType.NUMERIC) {
                    servicioId = (long) row.getCell(28).getNumericCellValue();
                    ServicioSalud servicioSalud = new ServicioSalud();
                    servicioSalud.setId(servicioId);
                    establecimiento.setServicio(servicioSalud);
                } else {
                    errorFlag = true;
                    errorMessages.add("El servicioId '" + servicioId + "' no es válido, en linea N° " + numeroRegistro);
                }

                // Del tercer Constructor en model -> entity -> Establecimiento
                establecimientosArrayList.add(new Establecimiento(establecimiento.getAcompanamiento(), establecimiento.getDIR(), establecimiento.getNivel_Atencion(),
                        establecimiento.getCodigoAntiguo(), establecimiento.getCodigoNuevo(), establecimiento.getComunaMayusc(), establecimiento.getComunaNombre(),
                        establecimiento.getDependencia(), establecimiento.getElimID(), establecimiento.getEstablecimientoNombre(), establecimiento.getIaaps(),
                        establecimiento.getIdComunaTexto(), establecimiento.getIdDependencia(), establecimiento.getIdMacroZona(), establecimiento.getIdOrdenServicio(),
                        establecimiento.getIdServicioIdComuna(), establecimiento.getIdTipo(), establecimiento.getIdTipoEst(), establecimiento.getMacroZona(),
                        establecimiento.getRegionAlias(), establecimiento.getRegionCodigo(), establecimiento.getRegionNombre(), establecimiento.getRegionOrden(),
                        establecimiento.getServicioNombre(), establecimiento.getTipoEstablecimiento(), establecimiento.getComuna(), establecimiento.getRegion(),
                        establecimiento.getServicio()));
            }

            if (errorFlag) {
                flash.addFlashAttribute("errorMessages", errorMessages);
                return "redirect:/carga-masiva-establecimientos/index";
            } else {

                for(Establecimiento desc: establecimientosArrayList) {
                    establecimientoDaoJpaRepo.save(desc);
                }

                flash.addFlashAttribute("success", "Archivo cargado correctamente");
            }

        } else {
            //
            logger.info("El archivo a cargar debe ser de tipo Excel con extención xlsx");
            flash.addFlashAttribute("error", "El archivo a cargar debe ser de tipo Excel con extención xlsx");
        }

        return "redirect:/carga-masiva-establecimientos/index";
    }
}
