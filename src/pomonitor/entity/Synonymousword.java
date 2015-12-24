package pomonitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Synonymousword entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="synonymousword"
    ,catalog="pomonitor"
)

public class Synonymousword  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String category;
     private String words;


    // Constructors

    /** default constructor */
    public Synonymousword() {
    }

    
    /** full constructor */
    public Synonymousword(String category, String words) {
        this.category = category;
        this.words = words;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="category")

    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    @Column(name="words")

    public String getWords() {
        return this.words;
    }
    
    public void setWords(String words) {
        this.words = words;
    }
   








}