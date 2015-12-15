package pomonitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Emotionalword entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emotionalword", catalog = "pomonitor")
public class Emotionalword implements java.io.Serializable {

	// Fields

	private Integer id;
	private String word;
	private String speech;
	private Integer polarity;
	private Integer strength;

	// Constructors

	/** default constructor */
	public Emotionalword() {
	}

	/** minimal constructor */
	public Emotionalword(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Emotionalword(Integer id, String word, String speech,
			Integer polarity, Integer strength) {
		this.id = id;
		this.word = word;
		this.speech = speech;
		this.polarity = polarity;
		this.strength = strength;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "word")
	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "speech")
	public String getSpeech() {
		return this.speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	@Column(name = "polarity")
	public Integer getPolarity() {
		return this.polarity;
	}

	public void setPolarity(Integer polarity) {
		this.polarity = polarity;
	}

	@Column(name = "strength")
	public Integer getStrength() {
		return this.strength;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	public String toString() {
		return new String(this.word + " " + this.speech + " " + this.polarity
				+ " " + this.strength);
	}
}