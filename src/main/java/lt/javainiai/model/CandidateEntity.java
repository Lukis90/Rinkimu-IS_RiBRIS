package lt.javainiai.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Candidates")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long personsId;
    
    private Long listPossition;

    @NotNull
    @Length(min = 1, max = 40)
    private String name;

    @NotNull
    @Length(min = 1, max = 40)
    private String surname;

    @NotNull
    @Column(name = "Date_of_Birth")
    private Date birthDate;

    @ManyToOne
    @JsonBackReference(value = "candidate-party")
    @JoinColumn(name = "Party_Id")
    private PartyEntity party;

    @JsonProperty
    public String getPartyName() {
        return party == null ? null : party.getName();
    }
    
    @JsonProperty
    public Long getConstituencyId() {
        return constituency == null ? null : constituency.getId();
    }
    
    @JsonProperty
    public String getConstituencyName() {
        return constituency == null ? null : constituency.getName();
    }
    
    @OneToMany(mappedBy = "candidate", cascade=CascadeType.ALL)
    @JsonManagedReference(value = "candidate-ratingResults")
    private List<CandidatesResultsRatingEntity> candidatesResultsRating;
    
    @OneToMany(mappedBy = "candidate", cascade=CascadeType.ALL)
    @JsonManagedReference(value = "candidate-singleMandateResults")
    private List<CandidatesResultsSingleMandateEntity> candidatesResultsSingleMandate;
    
    private Boolean multiMandate;

    private String biography;

    @ManyToOne
    @JsonBackReference(value = "candidate-constituency")
    @JoinColumn(name = "Constituency_Id")
    private ConstituencyEntity constituency;

    // Constructor
    public CandidateEntity() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonsId() {
		return personsId;
	}

	public void setPersonsId(Long personsId) {
		this.personsId = personsId;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public PartyEntity getParty() {
        return party;
    }

    public void setParty(PartyEntity party) {
        this.party = party;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

	public List<CandidatesResultsRatingEntity> getCandidatesResultsRating() {
		return candidatesResultsRating;
	}

	public void setCandidatesResultsRating(List<CandidatesResultsRatingEntity> candidatesResultsRating) {
		this.candidatesResultsRating = candidatesResultsRating;
	}

	public List<CandidatesResultsSingleMandateEntity> getCandidatesResultsSingleMandate() {
		return candidatesResultsSingleMandate;
	}

	public void setCandidatesResultsSingleMandate(
			List<CandidatesResultsSingleMandateEntity> candidatesResultsSingleMandate) {
		this.candidatesResultsSingleMandate = candidatesResultsSingleMandate;
	}

	public Boolean getMultiMandate() {
		return multiMandate;
	}

	public void setMultiMandate(Boolean multiMandate) {
		this.multiMandate = multiMandate;
	}

	public ConstituencyEntity getConstituency() {
        return constituency;
    }

    public void setConstituency(ConstituencyEntity constituency) {
        this.constituency = constituency;
    }
    
    public Long getListPossition() {
		return listPossition;
	}

	public void setListPossition(Long listPossition) {
		this.listPossition = listPossition;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CandidateEntity other = (CandidateEntity) obj;
		if (personsId == null) {
			if (other.personsId != null)
				return false;
		} else if (!personsId.equals(other.personsId))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "CandidateEntity [id=" + id + ", name=" + name + ", surname=" + surname + ", birthDate=" + birthDate
                + ", party=" + party + ", biography=" + biography + ", constituency=" + constituency + "]";
    }

}
