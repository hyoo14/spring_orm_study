//package hellojpa;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@SequenceGenerator(name = "MEMBER_SEQ_GEN", sequenceName = "MEMBER_SEQ")
////@TableGenerator(name = "MEMBER_TB_GEN", table = "MY_SEQ", pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
////@Table(name="TEMP")
//public class Member {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GEN")
////    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_TB_GEN")
//    private Long id;
//
//    @Column(nullable=false)
//    private String name;
//
//    private Integer age;
//
//    @Enumerated(EnumType.STRING)
//    private RoleType roleType;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//
//    @Lob
//    private String description;
//
//    @Transient
//    private int temp;
//
//    public Member() {
//    }
//}
