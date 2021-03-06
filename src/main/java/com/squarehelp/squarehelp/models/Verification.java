package com.squarehelp.squarehelp.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import javax.persistence.*;

@Entity
@Table(name = "verifications_req")
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT")
    private int originator_user_id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String approver_name;

    @Column(columnDefinition = "VARCHAR(255)")
    private String sender_name;

    @Column(columnDefinition = "DATE")
    private String day_created;

    @Column(columnDefinition = "INT")
    private int days_smoke_free;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean is_approved;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean is_pending;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean is_changes_updated;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user_veq;

    public Verification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOriginator_user_id() {
        return originator_user_id;
    }

    public void setOriginator_user_id(int originator_user_id) {
        this.originator_user_id = originator_user_id;
    }

    public String getApprover_name() {
        return approver_name;
    }

    public void setApprover_name(String approver_name) {
        this.approver_name = approver_name;
    }

    public String getDay_created() {
        return day_created;
    }

    public void setDay_created(String day_created) {
        this.day_created = day_created;
    }

    public int getDays_smoke_free() {
        return days_smoke_free;
    }

    public void setDays_smoke_free(int days_smoke_free) {
        this.days_smoke_free = days_smoke_free;
    }

    public Boolean getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(Boolean is_approved) {
        this.is_approved = is_approved;
    }

    public Boolean getIs_pending() { return is_pending; }

    public void setIs_pending(Boolean is_pending) { this.is_pending = is_pending; }

    public Boolean getIs_changes_updated() { return is_changes_updated; }

    public void setIs_changes_updated(Boolean is_changes_updated) {this.is_changes_updated = is_changes_updated; }

    public String getSender_name() { return sender_name; }

    public void setSender_name(String sender_name) { this.sender_name = sender_name; }

    public Verification(int originator_user_id, String approver_name, String day_created, int days_smoke_free, Boolean is_approved, User user_veq, Boolean is_pending, Boolean is_changes_updated, String sender_name) {
        this.originator_user_id = originator_user_id;
        this.sender_name = sender_name;
        this.approver_name = approver_name;
        this.day_created = day_created;
        this.days_smoke_free = days_smoke_free;
        this.is_approved = is_approved;
        this.user_veq = user_veq;
        this.is_pending = is_pending;
        this.is_changes_updated = is_changes_updated;
    }

    public User getUser_veq() {
        return user_veq;
    }

    public void setUser_veq(User user_veq) {
        this.user_veq = user_veq;
    }
}
