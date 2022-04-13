package com.switchkit.switchkit_test.incidentReports;

import com.switchkit.switchkit_test.users.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "incident_reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    private String title;
    private Status status;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public Report(User author, String title) {
        this.author = author;
        this.title = title;
        this.status = Status.NEW;
    }

    public Report(User author, User assignee, String title) {
        this.author = author;
        this.assignee = assignee;
        this.title = title;
        this.status = Status.ASSIGNED;
    }
}
