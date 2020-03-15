package me.decentos.core.model;

import me.decentos.orm.Id;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    @Id
    private long no;
    private String type;
    private BigDecimal rest;

    public long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getRest() {
        return rest;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (no != account.no) return false;
        if (!Objects.equals(type, account.type)) return false;
        return Objects.equals(rest, account.rest);
    }

    @Override
    public int hashCode() {
        int result = (int) (no ^ (no >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (rest != null ? rest.hashCode() : 0);
        return result;
    }
}
