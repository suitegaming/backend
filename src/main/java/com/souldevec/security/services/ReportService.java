package com.souldevec.security.services;

import com.souldevec.security.dtos.DailySummaryDto;
import com.souldevec.security.dtos.MonthlyReportDto;
import com.souldevec.security.dtos.ProductSalesDto;
import com.souldevec.security.dtos.SnackProfitDto;
import com.souldevec.security.entities.Gasto;
import com.souldevec.security.entities.InventoryMovement;
import com.souldevec.security.entities.Product;
import com.souldevec.security.entities.Turno;
import com.souldevec.security.enums.MovementType;
import com.souldevec.security.repositories.GastoRepository;
import com.souldevec.security.repositories.InventoryMovementRepository;
import com.souldevec.security.repositories.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    public MonthlyReportDto getMonthlyReport(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Turno> turnosDelMes = turnoRepository.findByFechaBetween(startDate, endDate);
        Map<LocalDate, List<Turno>> turnosPorDia = turnosDelMes.stream()
                .collect(Collectors.groupingBy(Turno::getFecha));

        MonthlyReportDto monthlyReport = new MonthlyReportDto();
        List<DailySummaryDto> dailySummaries = new ArrayList<>();

        Optional<Turno> ultimoTurnoDiaAnterior = turnoRepository.findTopByFechaOrderByHoraSalidaDesc(startDate.minusDays(1));
        BigDecimal kwLecturaAnterior = ultimoTurnoDiaAnterior.map(Turno::getKw).orElse(BigDecimal.ZERO);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            DailySummaryDto dailySummary = new DailySummaryDto();
            dailySummary.setDate(date);

            List<Turno> turnosDelDia = turnosPorDia.get(date);

            if (turnosDelDia != null && !turnosDelDia.isEmpty()) {
                for (Turno turno : turnosDelDia) {
                    dailySummary.setTotalEfectivo(dailySummary.getTotalEfectivo().add(turno.getEfectivo()));
                    dailySummary.setTotalYape(dailySummary.getTotalYape().add(turno.getYape()));
                    dailySummary.setTotalSnacks(dailySummary.getTotalSnacks().add(turno.getSnacks()));
                    dailySummary.setTotalConsumo(dailySummary.getTotalConsumo().add(turno.getConsumo()));
                    dailySummary.setTotalRetiros(dailySummary.getTotalRetiros().add(turno.getRetiros()));
                    dailySummary.setTotalIngresoInventario(dailySummary.getTotalIngresoInventario().add(turno.getIngresoInventario()));
                    dailySummary.setTotalUsuarios(dailySummary.getTotalUsuarios() + turno.getUsuarios());
                    dailySummary.setTotalDineroPancafe(dailySummary.getTotalDineroPancafe().add(turno.getDineroPancafe()));
                    dailySummary.setTotalUsanzaPancafe(dailySummary.getTotalUsanzaPancafe().add(turno.getUsanzaPancafe()));
                }

                BigDecimal totalIngresosDia = dailySummary.getTotalDineroPancafe().add(dailySummary.getTotalSnacks());
                dailySummary.setTotalIngresos(totalIngresosDia);
                
                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = date.atTime(23, 59, 59);
                List<Gasto> gastosDelDia = gastoRepository.findByTimestampBetween(startOfDay, endOfDay);

                BigDecimal totalGastosCalculados = gastosDelDia.stream()
                    .filter(gasto -> gasto.getAmount().compareTo(BigDecimal.ZERO) > 0 &&
                                     !gasto.getDescription().toLowerCase().startsWith("deposito"))
                    .map(Gasto::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                dailySummary.setTotalGastos(totalGastosCalculados);
                dailySummary.setDiferenciaDia(totalIngresosDia.subtract(totalGastosCalculados).subtract(dailySummary.getTotalEfectivo()).subtract(dailySummary.getTotalYape()));

                // Calcular KW consumidos
                BigDecimal kwLecturaActual = turnosDelDia.stream()
                        .max(Comparator.comparing(Turno::getId))
                        .map(Turno::getKw)
                        .orElse(kwLecturaAnterior);

                if (kwLecturaAnterior.compareTo(BigDecimal.ZERO) > 0) { // No calcular consumo para el primer día sin datos previos
                    dailySummary.setKwConsumidos(kwLecturaActual.subtract(kwLecturaAnterior));
                }
                kwLecturaAnterior = kwLecturaActual;

                if (dailySummary.getTotalUsanzaPancafe().compareTo(BigDecimal.ZERO) > 0) {
                    dailySummary.setRatioKw(dailySummary.getKwConsumidos().divide(dailySummary.getTotalUsanzaPancafe(), 2, RoundingMode.HALF_UP));
                }

            }
            dailySummaries.add(dailySummary);
        }

        monthlyReport.setDailySummaries(dailySummaries);

        // Calculate monthly totals
        for (DailySummaryDto daily : dailySummaries) {
            monthlyReport.setTotalEfectivoMes(monthlyReport.getTotalEfectivoMes().add(daily.getTotalEfectivo()));
            monthlyReport.setTotalYapeMes(monthlyReport.getTotalYapeMes().add(daily.getTotalYape()));
            monthlyReport.setTotalSnacksMes(monthlyReport.getTotalSnacksMes().add(daily.getTotalSnacks()));
            monthlyReport.setTotalConsumoMes(monthlyReport.getTotalConsumoMes().add(daily.getTotalConsumo()));
            monthlyReport.setTotalRetirosMes(monthlyReport.getTotalRetirosMes().add(daily.getTotalRetiros()));
            monthlyReport.setTotalIngresoInventarioMes(monthlyReport.getTotalIngresoInventarioMes().add(daily.getTotalIngresoInventario()));
            monthlyReport.setTotalUsuariosMes(monthlyReport.getTotalUsuariosMes() + daily.getTotalUsuarios());
            monthlyReport.setTotalKwConsumidosMes(monthlyReport.getTotalKwConsumidosMes().add(daily.getKwConsumidos()));
            monthlyReport.setTotalDineroPancafeMes(monthlyReport.getTotalDineroPancafeMes().add(daily.getTotalDineroPancafe()));
            monthlyReport.setTotalUsanzaPancafeMes(monthlyReport.getTotalUsanzaPancafeMes().add(daily.getTotalUsanzaPancafe()));
            monthlyReport.setTotalIngresosMes(monthlyReport.getTotalIngresosMes().add(daily.getTotalIngresos()));
            monthlyReport.setTotalGastosMes(monthlyReport.getTotalGastosMes().add(daily.getTotalGastos()));
        }
        monthlyReport.setDiferenciaMes(monthlyReport.getTotalIngresosMes().subtract(monthlyReport.getTotalGastosMes()).subtract(monthlyReport.getTotalEfectivoMes()).subtract(monthlyReport.getTotalYapeMes()));

        BigDecimal totalRatioKw = BigDecimal.ZERO;
        int daysWithRatio = 0;
        for (DailySummaryDto daily : dailySummaries) {
            if (daily.getRatioKw().compareTo(BigDecimal.ZERO) > 0) {
                totalRatioKw = totalRatioKw.add(daily.getRatioKw());
                daysWithRatio++;
            }
        }

        if (daysWithRatio > 0) {
            monthlyReport.setPromedioRatioKwMes(totalRatioKw.divide(new BigDecimal(daysWithRatio), 2, RoundingMode.HALF_UP));
        }

        return monthlyReport;
    }

    public List<ProductSalesDto> getBestSellingProducts(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<InventoryMovement> salesMovements = inventoryMovementRepository.findByTypeAndTimestampBetween(MovementType.OUT, startDateTime, endDateTime);

        Map<Product, Long> salesByProduct = salesMovements.stream()
                .collect(Collectors.groupingBy(
                        InventoryMovement::getProduct,
                        Collectors.summingLong(InventoryMovement::getQuantity)
                ));

        return salesByProduct.entrySet().stream()
                .map(entry -> new ProductSalesDto(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getValue()
                ))
                .sorted(Comparator.comparingLong(ProductSalesDto::getTotalQuantitySold).reversed())
                .collect(Collectors.toList());
    }

    public SnackProfitDto getSnackProfit(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<InventoryMovement> salesMovements = inventoryMovementRepository.findByTypeAndTimestampBetween(MovementType.OUT, startDateTime, endDateTime);

        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        for (InventoryMovement movement : salesMovements) {
            Product product = movement.getProduct();
            BigDecimal quantity = new BigDecimal(movement.getQuantity());

            totalRevenue = totalRevenue.add(product.getSellingPrice().multiply(quantity));
            totalCost = totalCost.add(product.getPurchasePrice().multiply(quantity));
        }

        BigDecimal totalProfit = totalRevenue.subtract(totalCost);

        return new SnackProfitDto(totalRevenue, totalCost, totalProfit, startDate, endDate);
    }
}

