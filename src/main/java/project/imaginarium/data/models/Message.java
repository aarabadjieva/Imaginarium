package project.imaginarium.data.models;

import jdk.jfr.BooleanFlag;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.users.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message extends BaseEntity{

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String about;

    @Column
    @Lob
    private String text;

    @Column(nullable = false)
    private String date;

    @Column(columnDefinition = "boolean default false")
    @BooleanFlag
    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "recipient", referencedColumnName = "id")
    private User recipient;

}
