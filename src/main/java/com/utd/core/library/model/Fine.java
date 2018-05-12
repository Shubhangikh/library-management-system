package com.utd.core.library.model;

import java.io.Serializable;

public class Fine implements Serializable {
    private static final long serialVersionUID = 5L;

    private int loanId;
    private double fineAmount;
    private boolean isPaid;

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Fine{" +
                "loanId=" + loanId +
                ", fineAmount=" + fineAmount +
                ", isPaid=" + isPaid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fine fine = (Fine) o;

        if (loanId != fine.loanId) return false;
        if (Double.compare(fine.fineAmount, fineAmount) != 0) return false;
        return isPaid == fine.isPaid;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = loanId;
        temp = Double.doubleToLongBits(fineAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isPaid ? 1 : 0);
        return result;
    }
}
