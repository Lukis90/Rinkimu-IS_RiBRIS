const React = require('react');

var CandidatesList = React.createClass({
  render: function() {
    var self = this;
    var nr = 1;
    var CandidatesList = this.props.candidates.filter(function(candidate) {
      if (candidate.constituencyName != null && candidate.partyName != null) {
        if ((candidate.name.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
           (candidate.surname.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
           (candidate.partyName.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
           (candidate.constituencyName.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1)) {
          return false;
        } else {
          return true;
        }
      } else if (candidate.constituencyName != null && candidate.partyName == null) {
        if ((candidate.name.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
           (candidate.surname.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
           (candidate.constituencyName.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1)) {
          return false;
        } else {
          return true;
        }
      } else if (candidate.partyName != null && candidate.constituencyName == null) {
        if ((candidate.name.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
           (candidate.surname.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
           (candidate.partyName.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1)) {
          return false;
        } else {
          return true;
        }
      } else {
        if ((candidate.name.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
           (candidate.surname.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1)) {
          return false;
        } else {
          return true;
        }
      }
    }).map(function(candidate, index) {
      return (
        <tr key={index}>
          <td>{nr++}</td>
          <td>{candidate.name.replace(/'/g,"")}</td>
          <td>{candidate.surname.replace(/'/g,"")}</td>
          <td>{candidate.birthDate}</td>
          <td>{candidate.partyName}</td>
          <td>{candidate.constituencyName}</td>
          <td>
            <button className="btn btn-danger btn-sm" onClick={self.props.onRemoveItem(candidate)}>
              <i className="fa fa-times" aria-hidden="true"></i>
            </button>
          </td>
        </tr>
      );
    });

    return (
      <div className="container-fluid">
        <div className="form-group pull-right">
          <input type="text" className="search form-control" placeholder="Ieškoti" onChange={this.props.onSearchTextChange} />
        </div>
        <div>
          <h3>
            <strong>Kandidatų sąrašas</strong>
          </h3>
        </div>
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Nr</th>
              <th>Vardas</th>
              <th>Pavardė</th>
              <th>Gimimo data</th>
              <th>Partija</th>
              <th>Apygarda</th>
              <th>Ištrinti</th>
            </tr>
          </thead>
          <tbody>
            {CandidatesList}
          </tbody>
        </table>
      </div>
    )
  }
});

module.exports = CandidatesList;
