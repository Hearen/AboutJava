package com.worksap.morphling.raptor.dump.thread.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.worksap.morphling.raptor.dump.thread.enums.DumpVersionEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "thread_dump")
@AllArgsConstructor
@NoArgsConstructor
public class ThreadDumpSimpleDo implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dumpedTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date parsedTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "dump_version")
    private DumpVersionEnum version;
    private String details;
    private String createdBy;
    private String checkSum;
}
