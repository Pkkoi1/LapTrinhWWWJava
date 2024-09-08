package vn.edu.iuh.fit.dtos;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link vn.edu.iuh.fit.entities.Account}
 */
public class AccountDto implements Serializable {
    private final String accountId;
    private final String fullName;
    private final String password;
    private final String email;
    private final String phone;
    private final Byte status;

    public AccountDto(String accountId, String fullName, String password, String email, String phone, Byte status) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }


    public String getAccountId() {
        return accountId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Byte getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto entity = (AccountDto) o;
        return Objects.equals(this.accountId, entity.accountId) &&
                Objects.equals(this.fullName, entity.fullName) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.status, entity.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, fullName, password, email, phone, status);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "accountId = " + accountId + ", " +
                "fullName = " + fullName + ", " +
                "password = " + password + ", " +
                "email = " + email + ", " +
                "phone = " + phone + ", " +
                "status = " + status + ")";
    }
}