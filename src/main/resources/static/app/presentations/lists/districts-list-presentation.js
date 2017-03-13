const React = require('react');
const Button = require('../add-button-presentation');
const ConfirmDelete = require('../../util/delete-confirm');

var DistrictListComponent = React.createClass({
  render: function() {
    var self = this;
    var style = {backgroundColor: "D52D2D"};
    var buttonText = <i className="fa fa-times" aria-hidden="true"></i>;
    var cancelText = "Atšaukti";
    var confirmText = "Patvirtinti";
    var nr = 1;
    var districtsList = this.props.districts.filter(function(district) {
      if ((district.name.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
         (district.address.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
         (district.constituencyName.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1)) {
        return false;
      } else {
        return true;
      }
    }).map(function(district, index) {
      var title = <h2>Ištrinste <b>{district.name}</b> apylinkę</h2>
      var body = <h4 className="alert alert-danger">Ar tiktai norite ištrinti <b>{district.name}</b> apylinkę?</h4>;
      return (
        <tr key={index}>
          <td>{nr++}</td>
          <td>{district.name}</td>
          <td>{district.address}</td>
          <td>{district.numOfVoters}</td>
          <td>{district.constituencyName}</td>
          <td><Button representative={district.representative} id={district.id}/></td>
          <td>
            <button className="btn btn-success btn-sm" onClick={self.props.onEditItem(district)}>
              <i className="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <ConfirmDelete
              title={title}
              body={body}
              style={style}
              buttonText={buttonText}
              cancelText={cancelText}
              confirmText={confirmText}
              onConfirm={self.props.onRemoveItem(district)}
            />
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
            <strong>Apylinkių sąrašas</strong>
          </h3>
        </div>
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Nr</th>
              <th>Pavadinimas</th>
              <th>Adresas</th>
              <th>Balsuotojų skaičius</th>
              <th>Apygarda</th>
              <th>Apylinkės atstovas</th>
              <th>Redaguoti</th>
            </tr>
          </thead>
          <tbody>
            {districtsList}
          </tbody>
        </table>
      </div>
    );
  }
});

DistrictListComponent.propTypes = {
  onRemoveItem: React.PropTypes.func.isRequired,
  districts: React.PropTypes.array.isRequired,
  onEditItem: React.PropTypes.func.isRequired
}

module.exports = DistrictListComponent;
