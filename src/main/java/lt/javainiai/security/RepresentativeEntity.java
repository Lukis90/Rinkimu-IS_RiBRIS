package lt.javainiai.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lt.javainiai.model.PollingDistrictEntity;

@Entity
@Table(name = "Polling_District_Representatives")
public class RepresentativeEntity {
	
//	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min = 1, max = 40)
    private String name;

    @Length(min = 1, max = 40)
    private String surname;
    
    @ManyToOne
    private Admin admin;
    
    @JsonIgnore
    private String password;

    // Bidirectional OneToOne
    @OneToOne
    @JsonBackReference(value = "pollingDistrict-representative")
    @JoinColumn(name = "Polling_District_Id")
    private PollingDistrictEntity pollingDistrict;

    @Email
    @Length(min = 1, max = 50)
    private String email;
    
    @JsonProperty
    public String getDistrictName() {
        return pollingDistrict == null ? null : pollingDistrict.getName();
    }
    
    @JsonProperty
    public Long getDistrictId() {
        return pollingDistrict == null ? null : pollingDistrict.getId();
    }

    // Constructor
    public RepresentativeEntity() {
    }

    // Setters and Getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public PollingDistrictEntity getPollingDistrict() {
        return pollingDistrict;
    }

    public void setPollingDistrict(PollingDistrictEntity pollingDistrict) {
        this.pollingDistrict = pollingDistrict;
    }

    public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}