package com.worksap.morphling.raptor.dump.thread.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;
import com.worksap.morphling.raptor.dump.thread.util.ListConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "dumpDo")
@Builder
@Entity
@Table(name = "thread_info")
@AllArgsConstructor
@NoArgsConstructor
public class ThreadDo implements Serializable {
    private static final long serialVersionUID = -1L;
    String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_dump_id")
    @JsonIgnore
    private ThreadDumpDo dumpDo;

    @Enumerated(EnumType.STRING)
    private StateEnum stateEnum;
    private boolean daemon;
    private boolean belongsToGc;
    private boolean belongsToFinalizer;
    private int priority;
    private int osPriority;
    private long threadId;
    private long nativeThreadId;

    @Convert(converter = ListConverter.class)
    private List<String> locksHeld;

    @Convert(converter = ListConverter.class)
    private List<String> locksWaiting;

    @Convert(converter = ListConverter.class)
    private List<String> callStack;

    @Convert(converter = ListConverter.class)
    private List<String> details;
}
