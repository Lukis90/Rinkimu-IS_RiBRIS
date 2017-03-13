const React = require('react');
const ConfirmDelete = require('../../util/delete-confirm');

var RepresentativeListComponent = React.createClass({
  render: function() {
    var self = this;
    var style = {backgroundColor: "D52D2D"};
    var buttonText = <i className="fa fa-times" aria-hidden="true"></i>;
    var cancelText = "Atšaukti";
    var confirmText = "Patvirtinti";
    var nr = 1;
    var RepresentativesList = this.props.representativesList.filter(function(representative) {
      if ((representative.name.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1) &&
          (representative.surname.toLowerCase().indexOf(self.props.searchText.toLowerCase()) === -1)) {
        return false;
      } else {
        return true;
      }
    }).map(function(representative, index) {
      var title = <h2>Ištrinste <b>{representative.name} {representative.surname}</b> atstovą</h2>
      var body = <h4 className="alert alert-danger">Ar tiktai norite
        ištrinti <b>{representative.name} {representative.surname}</b> atstovą?</h4>;
      return (
        <tr key={index}>
          <td>{nr++}</td>
          <td>{representative.name}</td>
          <td>{representative.surname}</td>
          <td>{representative.districtName}</td>
          <td>
            <button className="btn btn-success btn-sm" onClick={self.props.onEditItem(representative)}>
              <i className="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <ConfirmDelete
              title={title}
              body={body}
              style={style}
              buttonText={buttonText}
              cancelText={cancelText}
              confirmText={confirmText}
              onConfirm={self.props.onRemoveItem(representative)}
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
            <strong>Atstovų sąrašas</strong>
          </h3>
        </div>
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Nr</th>
              <th>Vardas</th>
              <th>Pavardė</th>
              <th>Apylinkė</th>
              <th>Ištrinti</th>
            </tr>
          </thead>
          <tbody>
            {RepresentativesList}
          </tbody>
        </table>
      </div>
    );
  }
});

/*RepresentativeListComponent.propTypes = {
  onRemoveItem: React.PropTypes.func.isRequired,
  districts: React.PropTypes.array.isRequired,
  onEditItem: React.PropTypes.func.isRequired
}*/

module.exports = RepresentativeListComponent;
