package com.souldevec.security.services;

import com.souldevec.security.dtos.DailySummaryDto;
import com.souldevec.security.dtos.MonthlyReportDto;
import com.souldevec.security.entities.Turno;
import com.souldevec.security.repositories.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private TurnoRepository turnoRepository;

    public MonthlyReportDto getMonthlyReport(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Turno> turnos = turnoRepository.findByFechaBetween(startDate, endDate);

        Map<LocalDate, List<Turno>> turnosByDay = turnos.stream()
                .collect(Collectors.groupingBy(Turno::getFecha));

        MonthlyReportDto monthlyReport = new MonthlyReportDto();

        List<DailySummaryDto> dailySummaries = turnosByDay.entrySet().stream()
                .map(entry -> {
                    DailySummaryDto dailySummary = new DailySummaryDto();
                    dailySummary.setDate(entry.getKey());

                    for (Turno turno : entry.getValue()) {
                        dailySummary.setTotalEfectivo(dailySummary.getTotalEfectivo().add(turno.getEfectivo()));
                        dailySummary.setTotalYape(dailySummary.getTotalYape().add(turno.getYape()));
                        dailySummary.setTotalSnacks(dailySummary.getTotalSnacks().add(turno.getSnacks()));
                        dailySummary.setTotalConsumo(dailySummary.getTotalConsumo().add(turno.getConsumo()));
                        dailySummary.setTotalRetiros(dailySummary.getTotalRetiros().add(turno.getRetiros()));
                        dailySummary.setTotalIngresoInventario(dailySummary.getTotalIngresoInventario().add(turno.getIngresoInventario()));
                        dailySummary.setTotalUsuarios(dailySummary.getTotalUsuarios() + turno.getUsuarios());
                        dailySummary.setTotalKw(dailySummary.getTotalKw().add(turno.getKw()));
                        dailySummary.setTotalDineroPancafe(dailySummary.getTotalDineroPancafe().add(turno.getDineroPancafe()));
                        dailySummary.setTotalUsanzaPancafe(dailySummary.getTotalUsanzaPancafe().add(turno.getUsanzaPancafe()));
                    }

                    BigDecimal totalIngresosDia = dailySummary.getTotalDineroPancafe().add(dailySummary.getTotalSnacks());
                    BigDecimal totalGastosDia = dailySummary.getTotalRetiros().add(dailySummary.getTotalConsumo());
                    dailySummary.setTotalIngresos(totalIngresosDia);
                    dailySummary.setTotalGastos(totalGastosDia);
                    dailySummary.setDiferenciaDia(totalIngresosDia.subtract(totalGastosDia).subtract(dailySummary.getTotalEfectivo()).subtract(dailySummary.getTotalYape()));

                    return dailySummary;
                })
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .collect(Collectors.toList());

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
            monthlyReport.setTotalKwMes(monthlyReport.getTotalKwMes().add(daily.getTotalKw()));
            monthlyReport.setTotalDineroPancafeMes(monthlyReport.getTotalDineroPancafeMes().add(daily.getTotalDineroPancafe()));
            monthlyReport.setTotalUsanzaPancafeMes(monthlyReport.getTotalUsanzaPancafeMes().add(daily.getTotalUsanzaPancafe()));
            monthlyReport.setTotalIngresosMes(monthlyReport.getTotalIngresosMes().add(daily.getTotalIngresos()));
            monthlyReport.setTotalGastosMes(monthlyReport.getTotalGastosMes().add(daily.getTotalGastos()));
        }
        monthlyReport.setDiferenciaMes(monthlyReport.getTotalIngresosMes().subtract(monthlyReport.getTotalGastosMes()).subtract(monthlyReport.getTotalEfectivoMes()).subtract(monthlyReport.getTotalYapeMes()));

        return monthlyReport;
    }
}
