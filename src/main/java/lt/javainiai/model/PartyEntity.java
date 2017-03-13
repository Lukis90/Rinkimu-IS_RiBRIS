package lt.javainiai.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Parties")
public class PartyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min = 1, max = 200)
    private String name;

    @Column(name = "Party_Number")
    private Long partyNo;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "candidate-party")
    private List<CandidateEntity> candidates;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "party-results")
    private List<PartyResultsEntity> partyResults;

    // Constructor
    public PartyEntity() {
    }

    // Getters and Setters
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

    public Long getPartyNo() {
        return partyNo;
    }

    public void setPartyNo(Long partyNo) {
        this.partyNo = partyNo;
    }

    public List<CandidateEntity> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateEntity> candidates) {
        this.candidates = candidates;
    }

    public List<PartyResultsEntity> getPartyResults() {
        return partyResults;
    }

    public void setPartyResults(List<PartyResultsEntity> partyResults) {
        this.partyResults = partyResults;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((candidates == null) ? 0 : candidates.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((partyNo == null) ? 0 : partyNo.hashCode());
        result = prime * result + ((partyResults == null) ? 0 : partyResults.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PartyEntity other = (PartyEntity) obj;
        if (candidates == null) {
            if (other.candidates != null)
                return false;
        } else if (!candidates.equals(other.candidates))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (partyNo == null) {
            if (other.partyNo != null)
                return false;
        } else if (!partyNo.equals(other.partyNo))
            return false;
        if (partyResults == null) {
            if (other.partyResults != null)
                return false;
        } else if (!partyResults.equals(other.partyResults))
            return false;
        return true;
    }

}
