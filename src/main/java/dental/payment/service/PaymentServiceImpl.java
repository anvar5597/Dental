/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/10/2024
 * Time:12:13 PM
 */


package dental.payment.service;

import dental.exception.ResourceNotFoundException;
import dental.patient_history.entity.PatientHistoryEntity;
import dental.patient_history.service.PatientHistoryService;
import dental.payment.dto.PaymentRequestDto;
import dental.payment.dto.PaymentResponseDto;
import dental.payment.entity.PaymentEntity;
import dental.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final PatientHistoryService historyService;


    @Override
    public String create(PaymentRequestDto dto) {
        PatientHistoryEntity patientHistory = historyService.getPatientById(dto.getId());
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaidValue(dto.getPaidValue());
        paymentEntity.setPatientHistoryEntity(patientHistory);
        paymentEntity.setPaidDate(LocalDate.now());
        paymentEntity.setDeleted(false);
        repository.save(paymentEntity);

        Integer total = patientHistory.getTotal();
        Integer paid = patientHistory.getPaid() + dto.getPaidValue();
        patientHistory.setPaid(paid);

        paymentEntity.getPatientHistoryEntity().getClient().setDebt(
                paymentEntity.getPatientHistoryEntity().getClient().getDebt()-paid);
        if (total <= paid) {
            patientHistory.setIsPaid(true);
        }
        historyService.save(patientHistory);
        return paid + " so`m to`landi" + "\n" + (total - paid) + " so`m qoldi";
    }

    @Override
    public String update(PaymentRequestDto dto, Long id) {
        PatientHistoryEntity patientHistory = historyService.getPatientById(dto.getId());
        Optional<PaymentEntity> optionalPaymentEntity = repository.findById(id);
        if (optionalPaymentEntity.isEmpty()) {
            throw new ResourceNotFoundException("Bunday " + id + " to`lov yo`q");
        }
        PaymentEntity paymentEntity = optionalPaymentEntity.get();
        paymentEntity.setPaidValue(dto.getPaidValue());
        paymentEntity.setPatientHistoryEntity(patientHistory);
        paymentEntity.setPaidDate(LocalDate.now());
        repository.save(paymentEntity);

        Integer total = patientHistory.getTotal();
        Integer paid = patientHistory.getPaid() + dto.getPaidValue();
        patientHistory.setPaid(paid);

        if (total <= paid) {
            patientHistory.setIsPaid(true);
        }
        historyService.save(patientHistory);

        return paid + " so`m to`landi";
    }

    @Override
    public List<PaymentResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> paymentByDebt() {
        return repository.findAll()
                .stream()
                .filter(payment -> !payment.getPatientHistoryEntity().getIsPaid())
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> paymentsByClient(Long id) {


        return repository.findAllByPatientHistoryEntityClient_Id(id)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> paymentDate(LocalDate date) {

        return repository.findAllByPaidDate(date)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> paymentByDoctor(Long id) {
        return repository.findAllByPatientHistoryEntityEmployees_Id(id)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> paymentsDate(LocalDate startDate, LocalDate endDate) {

        List<PaymentEntity> paymentEntities = repository.findAllByPaidDateBetween(startDate, endDate);

        List<PaymentResponseDto> responseDtos;

        responseDtos = paymentEntities.stream().map(this::toDto).toList();

        return responseDtos;

    }

    @Override
    public void exportToExcel(String filePath) throws IOException {
        List<PaymentResponseDto> dtoList = new ArrayList<>();
        for (PaymentEntity entity : repository.findAll()) {
            PaymentResponseDto dto = toDto(entity);
            dtoList.add(dto);
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("To`lov jadvali");

        Row headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("№");
        headRow.createCell(1).setCellValue("Familiya");
        headRow.createCell(2).setCellValue("Ism");
        headRow.createCell(3).setCellValue("Summa");
        headRow.createCell(4).setCellValue("Sanasi");

        // Sana uchun CellStyle yaratish va formatlash
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.yyyy"));

        // Hujayralarga chiziqlar qo'shadigan umumiy CellStyle yaratish
        CellStyle borderedCellStyle = workbook.createCellStyle();
        borderedCellStyle.setBorderTop(BorderStyle.THIN);
        borderedCellStyle.setBorderBottom(BorderStyle.THIN);
        borderedCellStyle.setBorderLeft(BorderStyle.THIN);
        borderedCellStyle.setBorderRight(BorderStyle.THIN);

        dateCellStyle.setBorderTop(BorderStyle.THIN);
        dateCellStyle.setBorderBottom(BorderStyle.THIN);
        dateCellStyle.setBorderLeft(BorderStyle.THIN);
        dateCellStyle.setBorderRight(BorderStyle.THIN);

        for (int i = 0; i < 5; i++) {
            Cell cell = headRow.getCell(i);
            if (cell == null) cell = headRow.createCell(i);
            cell.setCellStyle(borderedCellStyle);
        }

        int rowNum = 1;
        for (PaymentResponseDto item : dtoList) {
            Row row = sheet.createRow(rowNum);
            Cell idCell = row.createCell(0);
            idCell.setCellValue(rowNum);
            idCell.setCellStyle(borderedCellStyle);

            Cell lNameCell = row.createCell(1);
            lNameCell.setCellValue(item.getPatientLastName());
            lNameCell.setCellStyle(borderedCellStyle);

            Cell nameCell = row.createCell(2);
            nameCell.setCellValue(item.getPatientName());
            nameCell.setCellStyle(borderedCellStyle);

            Cell paidCell = row.createCell(3);
            paidCell.setCellValue(item.getPaid());
            paidCell.setCellStyle(borderedCellStyle);

            LocalDate localDate = item.getPaidDate();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Cell dateCell = row.createCell(4);
            dateCell.setCellValue(date);
            dateCell.setCellStyle(dateCellStyle);
            rowNum++;
        }
        // Excel faylini saqlash
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        workbook.close();

    }

    @Override
    public PaymentResponseDto toDto(PaymentEntity entity) {
        PaymentResponseDto responseDto = new PaymentResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setPatientName(entity.getPatientHistoryEntity().getClient().getName());
        responseDto.setPatientLastName(entity.getPatientHistoryEntity().getClient().getLastName());
        responseDto.setTotalValue(entity.getPatientHistoryEntity().getTotal());
        responseDto.setPaid(entity.getPaidValue());
        responseDto.setDebit(entity.getPatientHistoryEntity().getTotal() - entity.getPatientHistoryEntity().getPaid());
        responseDto.setPaidDate(entity.getPaidDate());
        return responseDto;
    }

    @Override
    public void deleteWithEmployee(Long id) {
        List<PaymentEntity> paymentEntities = repository.findAll();
        for (PaymentEntity entity : paymentEntities){
            if(entity.getPatientHistoryEntity().getEmployees().getId().equals(id)){
                entity.setDeleted(true);
            }
        }
    }

    @Override
    public void deleteWithClient(Long id) {
        List<PaymentEntity> paymentEntities = repository.findAll();
        for (PaymentEntity entity : paymentEntities){
            if(entity.getPatientHistoryEntity().getClient().getId().equals(id)){
                entity.setDeleted(true);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Optional<PaymentEntity> payment = repository.findById(id);
        PaymentEntity entity;
        if (payment.isEmpty()) {
            throw new ResourceNotFoundException("Bunday "
                    + id + "li to`lov yo`q");
        } else {
            entity = payment.get();
            entity.setDeleted(true);
        }
    }
}
