package vn.edu.iuh.fit.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link vn.edu.iuh.fit.entities.Log}
 */
public class LogDto implements Serializable {
    private final Long id;
    private final String accountId;
    private final Instant loginTime;
    private final Instant logoutTime;
    private final String notes;

    public LogDto(Long id, String accountId, Instant loginTime, Instant logoutTime, String notes) {
        this.id = id;
        this.accountId = accountId;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public Instant getLoginTime() {
        return loginTime;
    }

    public Instant getLogoutTime() {
        return logoutTime;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogDto entity = (LogDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.accountId, entity.accountId) &&
                Objects.equals(this.loginTime, entity.loginTime) &&
                Objects.equals(this.logoutTime, entity.logoutTime) &&
                Objects.equals(this.notes, entity.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, loginTime, logoutTime, notes);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "accountId = " + accountId + ", " +
                "loginTime = " + loginTime + ", " +
                "logoutTime = " + logoutTime + ", " +
                "notes = " + notes + ")";
    }
}