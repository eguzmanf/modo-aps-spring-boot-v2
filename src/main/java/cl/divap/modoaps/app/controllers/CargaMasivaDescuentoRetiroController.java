package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.models.dao.descuentoRetiro.IDescuentoRetiro;
import cl.divap.modoaps.app.models.dao.comuna.IComunaDao;
import cl.divap.modoaps.app.models.entity.Comuna;
import cl.divap.modoaps.app.models.entity.DescuentoRetiro;
import cl.divap.modoaps.app.models.entity.ServicioSalud;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/carga-masiva-descuento-retiro")
public class CargaMasivaDescuentoRetiroController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Autowired
    private IComunaDao comunaDao;

    @Autowired
    private IDescuentoRetiro descuentoRetiroDao;

    @GetMapping(value={"", "/", "/index", "carga-archivo"})
    public String index(Model model) {
        //
        DescuentoRetiro descuentoRetiro = new DescuentoRetiro();

        model.addAttribute("titulo", "Ingreso Dotación - Carga masiva de registro Descuento al Retiro");
        model.addAttribute("descuentoRetiro", descuentoRetiro);

        return "carga-masiva-descuento-retiro/index";
    }

    @PostMapping(value="carga-archivo")
    public String cargaArchivo(@ModelAttribute DescuentoRetiro descuentoRetiro, RedirectAttributes flash, Model model, Authentication auth) throws IOException, ParseException {

        List<DescuentoRetiro> descuentoRetiroList = new ArrayList<DescuentoRetiro>();

        MultipartFile file = descuentoRetiro.getCargaMasiva();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

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
                        descuentoRetiro.setServicioSalud(servicioSaludEntity);
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
                        descuentoRetiro.setComuna(comunaEntity);
                    } else {
                        idComunaLong = null;
                        errorFlag = true;
                        errorMessages.add("La Comuna '" + comuna + "' no es válida, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Comuna con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                String fechaConvenioString = null;
                Date fechaConvenioDate = null;
                String fechaConvenioStringFormat = null;
                if(row.getCell(3).getCellType() == CellType.STRING) {
                    fechaConvenioString = row.getCell(3).getStringCellValue();    // 1957-02-27
                    try {
                        fechaConvenioDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaConvenioString); // Wed Feb 27 00:00:00 CLT 1957
                        descuentoRetiro.setFechaConvenio(fechaConvenioDate);

                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        fechaConvenioStringFormat = simpleDateFormat.format(fechaConvenioDate);
                    } catch (Exception e) {
                        errorFlag = true;
                        errorMessages.add("La Fecha Convenio '" + fechaConvenioString + "' no es válido, en linea N° " + numeroRegistro + ", ingrese formato 'yyyy-MM-dd'");
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Fecha Convenio con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro + ", ingrese formato 'yyyy-MM-dd'");
                }

                double numeroCuotasTotal = 0;
                if(row.getCell(4).getCellType() == CellType.NUMERIC) {
                    numeroCuotasTotal = row.getCell(4).getNumericCellValue();
                    descuentoRetiro.setNumeroCuotasTotal((int) numeroCuotasTotal);
                } else {
                    errorFlag = true;
                    errorMessages.add("Número Cuotas Total con formato inválido, debe de ser de tipo numérico, en linea N° " + numeroRegistro);
                }

                double montoCuotaMensual = 0;
                Long montoCuotaMensualLong = null;
                if(row.getCell(5).getCellType() == CellType.NUMERIC) {
                    montoCuotaMensual = row.getCell(5).getNumericCellValue();
                    try {
                        montoCuotaMensualLong = Double.valueOf(montoCuotaMensual).longValue();
                        descuentoRetiro.setMontoCuotaMensual(montoCuotaMensualLong);
                    } catch (NumberFormatException e) {
                        montoCuotaMensualLong = null;
                        errorFlag = true;
                        errorMessages.add("El Monto Cuota Mensual '" + montoCuotaMensual + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Monto Cuota Mensual con formato inválido, debe de ser de tipo numérico, en linea N° " + numeroRegistro);
                }

                double ultimaCuota = 0;
                Long ultimaCuotaLong = null;
                if(row.getCell(6).getCellType() == CellType.NUMERIC) {
                    ultimaCuota = row.getCell(6).getNumericCellValue();
                    try {
                        ultimaCuotaLong = Double.valueOf(ultimaCuota).longValue();
                        descuentoRetiro.setUltimaCuota(ultimaCuotaLong);
                    } catch (NumberFormatException e) {
                        ultimaCuotaLong = null;
                        errorFlag = true;
                        errorMessages.add("La Ultima Cuota '" + ultimaCuota + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Ultima Cuota con formato inválido, debe de ser de tipo numérico, en linea N° " + numeroRegistro);
                }

                double totalRecursos = 0;
                Long totalRecursosLong = null;
                if(row.getCell(7).getCellType() == CellType.NUMERIC) {
                    totalRecursos = row.getCell(7).getNumericCellValue();
                    try {
                        totalRecursosLong = Double.valueOf(totalRecursos).longValue();
                        descuentoRetiro.setTotalRecursos(totalRecursosLong);
                    } catch (NumberFormatException e) {
                        totalRecursosLong = null;
                        errorFlag = true;
                        errorMessages.add("El Total de Recursos '" + totalRecursos + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Total de Recursos con formato inválido, debe de ser de tipo numérico, en linea N° " + numeroRegistro);
                }

                double deuda = 0;
                Long deudaLong = null;
                if(row.getCell(8).getCellType() == CellType.NUMERIC) {
                    deuda = row.getCell(8).getNumericCellValue();
                    try {
                        deudaLong = Double.valueOf(deuda).longValue();
                        descuentoRetiro.setDeuda(deudaLong);
                    } catch (NumberFormatException e) {
                        deudaLong = null;
                        errorFlag = true;
                        errorMessages.add("La deuda '" + deuda + "' no es válido, en linea N° " + numeroRegistro);
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Deuda con formato inválido, debe de ser de tipo numérico, en linea N° " + numeroRegistro);
                }

                double numeroCuota = 0;
                if(row.getCell(9).getCellType() == CellType.NUMERIC) {
                    numeroCuota = row.getCell(9).getNumericCellValue();
                    descuentoRetiro.setNumeroCuota((int) numeroCuota);
                } else {
                    errorFlag = true;
                    errorMessages.add("Número Cuota con formato inválido, debe de ser de tipo numérico, en linea N° " + numeroRegistro);
                }

                String fechaCuotaString = null;
                Date fechaCuotaDate = null;
                String fechaCuotaStringFormat = null;
                if(row.getCell(10).getCellType() == CellType.STRING) {
                    fechaCuotaString = row.getCell(10).getStringCellValue();    // 1957-02-27
                    try {
                        fechaCuotaDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaCuotaString); // Wed Feb 27 00:00:00 CLT 1957
                        descuentoRetiro.setFechaCuota(fechaCuotaDate);

                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        fechaCuotaStringFormat = simpleDateFormat.format(fechaCuotaDate);
                    } catch (Exception e) {
                        errorFlag = true;
                        errorMessages.add("La Fecha Cuota '" + fechaCuotaString + "' no es válido, en linea N° " + numeroRegistro + ", ingrese formato 'yyyy-MM-dd'");
                    }
                } else {
                    errorFlag = true;
                    errorMessages.add("Fecha Cuota con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro + ", ingrese formato 'yyyy-MM-dd'");
                }

                String resolucion = null;
                if(row.getCell(11).getCellType() == CellType.STRING) {
                    resolucion = row.getCell(11).getStringCellValue();
                    descuentoRetiro.setResolucion(resolucion);
                } else {
                    errorFlag = true;
                    errorMessages.add("Resolución con formato inválido, debe de ser de tipo texto, en linea N° " + numeroRegistro);
                }

                descuentoRetiro.setCreateAt(new Date());

                System.out.println("N° de Registro: " + numeroRegistro);
                logger.info("Resolución: " + resolucion);

                descuentoRetiroList.add(new DescuentoRetiro(descuentoRetiro.getServicioSalud(), descuentoRetiro.getComuna(), descuentoRetiro.getFechaConvenio(), descuentoRetiro.getNumeroCuotasTotal(),
                        descuentoRetiro.getMontoCuotaMensual(), descuentoRetiro.getUltimaCuota(), descuentoRetiro.getTotalRecursos(), descuentoRetiro.getDeuda(), descuentoRetiro.getNumeroCuota(),
                        descuentoRetiro.getFechaCuota(), descuentoRetiro.getResolucion(), descuentoRetiro.getCreateAt()));
            }

            if (errorFlag) {
                flash.addFlashAttribute("errorMessages", errorMessages);
                return "redirect:/carga-masiva-descuento-retiro/index";
            } else {

                for(DescuentoRetiro desc: descuentoRetiroList) {
                    descuentoRetiroDao.save(desc);
                }

                flash.addFlashAttribute("success", "Archivo cargado correctamente");
            }

        } else {
            //
            logger.info("El archivo a cargar debe ser de tipo Excel con extención xlsx");
            flash.addFlashAttribute("error", "El archivo a cargar debe ser de tipo Excel con extención xlsx");
        }

        return "redirect:/carga-masiva-descuento-retiro/index";
    }
}
