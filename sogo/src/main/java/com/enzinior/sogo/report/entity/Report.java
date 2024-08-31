package com.enzinior.sogo.report.entity;

import com.enzinior.sogo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
public class Report {

    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportId;

    @Column(nullable = false)
    private int reportType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private long targetId;

    @ColumnDefault("false")
    private boolean processed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void changeProcessed() {
        this.processed = true;
    }

}
