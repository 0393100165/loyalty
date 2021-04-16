package loyalty.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_gift")
public class Gift {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "gift_code", unique = true, nullable = false, updatable = false)
	private String giftCode;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_id")
	private Person person;
	
	@Column(name = "create_date", updatable = false)
	private LocalDateTime createDate;
	
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
	
	public Gift() {
		createDate = LocalDateTime.now();
		modifiedDate = LocalDateTime.now();
	}

	public Gift(String giftCode, Person person) {
		super();
		this.id = 0;
		this.giftCode = giftCode;
		this.person = person;
		this.createDate = LocalDateTime.now();
		this.modifiedDate = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGiftCode() {
		return giftCode;
	}

	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "Gift [id=" + id + ", giftCode=" + giftCode + ", person=" + person + ", createDate=" + createDate
				+ ", modifiedDate=" + modifiedDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createDate, giftCode, id, modifiedDate, person);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Gift)) {
			return false;
		}
		Gift other = (Gift) obj;
		return Objects.equals(createDate, other.createDate) && Objects.equals(giftCode, other.giftCode)
				&& Objects.equals(id, other.id) && Objects.equals(modifiedDate, other.modifiedDate)
				&& Objects.equals(person, other.person);
	}
	
}
