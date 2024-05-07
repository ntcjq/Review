package com.sea.review.util;

import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 贷款利息计算器
 */
@Log
public class LoanInterestUtil {

    /**
     * 等额本息
     *
     * @param loanTerm
     * @param payoutAmountB
     * @param monthRateB
     */
    public static void averageCapitalPlusInterest(Integer loanTerm, BigDecimal payoutAmountB, BigDecimal monthRateB) {
        List<RepaymentSchedule> schedules = new ArrayList<>();
        for (int i = 1; i <= loanTerm; i++) {
            BigDecimal repaidCapitalAmt = schedules.stream().map(obj -> new BigDecimal(obj.getCapitalAmt())).reduce(BigDecimal.ZERO, BigDecimal::add);//累计已还本金
            BigDecimal initialPrincipal = payoutAmountB.subtract(repaidCapitalAmt);// 期初剩余本金
            Long totalAmt = 0l;
            Long interestAmt = 0l;
            Long capitalAmt = 0l;
            if (i != loanTerm) {
                totalAmt = payoutAmountB
                        .multiply(monthRateB)
                        .multiply(new BigDecimal(1).add(monthRateB).pow(loanTerm))
                        .divide(new BigDecimal(1).add(monthRateB).pow(loanTerm).subtract(new BigDecimal(1)), 0, BigDecimal.ROUND_HALF_UP)
                        .longValue();
                interestAmt = initialPrincipal
                        .multiply(monthRateB)
                        .setScale(0, BigDecimal.ROUND_HALF_UP).longValue();

                capitalAmt = new BigDecimal(totalAmt).subtract(new BigDecimal(interestAmt)).longValue();
            } else {//最后一期特殊处理
                capitalAmt = initialPrincipal.longValue();
                interestAmt = initialPrincipal
                        .multiply(monthRateB)
                        .setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
                totalAmt = new BigDecimal(capitalAmt).add(new BigDecimal(interestAmt)).longValue();
            }
            RepaymentSchedule schedule = new RepaymentSchedule();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, i);
            schedule.setPeriod(i);
            schedule.setTotalAmt(totalAmt);
            schedule.setCapitalAmt(capitalAmt);
            schedule.setInterestAmt(interestAmt);
            schedule.setInitialRemainingPrincipal(initialPrincipal.longValue());
            schedule.setEndingRemainingPrincipal(initialPrincipal.subtract(new BigDecimal(capitalAmt)).longValue());
            schedules.add(schedule);
            Integer period = schedule.getPeriod();
            String periodStr = period + "";
            if (period < 10) {
                periodStr = "00" + periodStr;

            } else if (period < 100) {
                periodStr = "0" + periodStr;
            }
            System.out.println("等额本息 第" + periodStr + "期 总金额:" + totalAmt
                    + " 本金:" + capitalAmt + " 利息:" + interestAmt
                    + " 期初剩余本金:" + schedule.getInitialRemainingPrincipal()
                    + " 期末剩余本金:" + schedule.getEndingRemainingPrincipal()
            );
        }
    }

    /**
     * 等额本金
     *
     * @param loanTerm
     * @param payoutAmountB
     * @param monthRateB
     */
    public static void averageCapital(Integer loanTerm, BigDecimal payoutAmountB, BigDecimal monthRateB) {
        List<RepaymentSchedule> schedules = new ArrayList<>();
        for (int i = 1; i <= loanTerm; i++) {
            BigDecimal repaidCapitalAmt = schedules.stream().map(obj -> new BigDecimal(obj.getCapitalAmt())).reduce(BigDecimal.ZERO, BigDecimal::add);//累计已还本金
            Long totalAmt = 0l;
            Long interestAmt = 0l;
            Long capitalAmt = 0l;
            if (i != loanTerm) {
                capitalAmt = payoutAmountB
                        .divide(new BigDecimal(loanTerm), 0, BigDecimal.ROUND_HALF_UP).longValue();
                interestAmt = payoutAmountB
                        .subtract(repaidCapitalAmt)
                        .multiply(monthRateB)
                        .setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
                totalAmt = new BigDecimal(capitalAmt).add(new BigDecimal(interestAmt)).longValue();
            } else {//最后一期特殊处理
                capitalAmt = payoutAmountB
                        .subtract(repaidCapitalAmt)
                        .setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
                interestAmt = payoutAmountB
                        .subtract(repaidCapitalAmt)
                        .multiply(monthRateB)
                        .setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
                totalAmt = new BigDecimal(capitalAmt).add(new BigDecimal(interestAmt)).longValue();
            }
            RepaymentSchedule schedule = new RepaymentSchedule();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, i);
            schedule.setPeriod(i);
            schedule.setTotalAmt(totalAmt);
            schedule.setCapitalAmt(capitalAmt);
            schedule.setInterestAmt(interestAmt);
            schedule.setInitialRemainingPrincipal(payoutAmountB.subtract(repaidCapitalAmt).longValue());
            schedule.setEndingRemainingPrincipal(payoutAmountB.subtract(repaidCapitalAmt).subtract(new BigDecimal(capitalAmt)).longValue());
            schedules.add(schedule);
            Integer period = schedule.getPeriod();
            String periodStr = period + "";
            if (period < 10) {
                periodStr = "00" + periodStr;

            } else if (period < 100) {
                periodStr = "0" + periodStr;
            }
            System.out.println("等额本金 第" + periodStr + "期 总金额:" + totalAmt
                    + " 本金:" + capitalAmt + " 利息:" + interestAmt
                    + " 期初剩余本金:" + schedule.getInitialRemainingPrincipal()
                    + " 期末剩余本金:" + schedule.getEndingRemainingPrincipal()
            );
        }
    }

    public static class RepaymentSchedule {
        /**
         * 还款期数
         */
        private Integer period;
        /**
         * 还款金额
         */
        private Long totalAmt;
        /**
         * 应还本金
         */
        private Long capitalAmt;
        /**
         * 应还利息
         */
        private Long interestAmt;
        /**
         * 期初剩余本金
         */
        private Long initialRemainingPrincipal;
        /**
         * 期末剩余本金
         */
        private Long endingRemainingPrincipal;

        public Integer getPeriod() {
            return period;
        }

        public void setPeriod(Integer period) {
            this.period = period;
        }

        public Long getTotalAmt() {
            return totalAmt;
        }

        public void setTotalAmt(Long totalAmt) {
            this.totalAmt = totalAmt;
        }

        public Long getCapitalAmt() {
            return capitalAmt;
        }

        public void setCapitalAmt(Long capitalAmt) {
            this.capitalAmt = capitalAmt;
        }

        public Long getInterestAmt() {
            return interestAmt;
        }

        public void setInterestAmt(Long interestAmt) {
            this.interestAmt = interestAmt;
        }

        public Long getInitialRemainingPrincipal() {
            return initialRemainingPrincipal;
        }

        public void setInitialRemainingPrincipal(Long initialRemainingPrincipal) {
            this.initialRemainingPrincipal = initialRemainingPrincipal;
        }

        public Long getEndingRemainingPrincipal() {
            return endingRemainingPrincipal;
        }

        public void setEndingRemainingPrincipal(Long endingRemainingPrincipal) {
            this.endingRemainingPrincipal = endingRemainingPrincipal;
        }

        public static void main(String[] args) {
            BigDecimal payoutAmountB = new BigDecimal(250000);//借款总金额
            BigDecimal monthRateB = new BigDecimal(3.10).divide(new BigDecimal(12*100), 12, BigDecimal.ROUND_HALF_UP);//月利率
            Integer loanTerm = 12 * 15;
            averageCapitalPlusInterest(loanTerm, payoutAmountB, monthRateB);

        }
    }
}
