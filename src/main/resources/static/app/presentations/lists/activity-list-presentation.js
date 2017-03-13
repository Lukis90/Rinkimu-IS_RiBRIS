const React = require('react');

var ActivityListComponent = React.createClass({
  render: function() {
    var self = this;
    var nr = 1;
    var activityList = this.props.constituencies.map(function(constituency, index) {
      return (
        <tr key={index}>
          <td>{nr++}</td>
          <td>{constituency.constituency.name}</td>
          <td>{constituency.givenBallots}</td>
          <td>{constituency.percentOfAllVoters}%</td>
        </tr>
      );
    });

    return (
      <div className="container-fluid">
        <div className="panel panel-default">
        <div className="panel-heading"><strong>Apygardų sąrašas</strong></div>
          <table className="table">
            <thead>
              <tr>
                <th>Nr</th>
                <th>Pavadinimas</th>
                <th>Balsavusių skaičius</th>
                <th>Balsai procentaliai</th>
              </tr>
            </thead>
            <tbody>
              {activityList}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
});

//DistrictListComponent.propTypes = {
//  onRemoveItem: React.PropTypes.func.isRequired,
//  districts: React.PropTypes.array.isRequired,
//  onEditItem: React.PropTypes.func.isRequired
//}

module.exports = ActivityListComponent;
