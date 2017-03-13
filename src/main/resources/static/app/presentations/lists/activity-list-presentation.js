const React = require('react');

var ActivityListComponent = React.createClass({
  render: function() {
    var self = this;
    var nr = 1;
    var activityList = this.props.districts.map(function(district, index) {
      return (
        <tr key={index}>
          <td>{nr++}</td>
          <td>{district.name}</td>
          <td>{district.numOfVoters}</td>
          <td>{district.constituencyName}</td>
          <td>{self.props.activity[index].sumOfGivenBallots}</td>
          <td>{self.props.percents[index].percentOfAllVoters}%</td>
        </tr>
      );
    });

    return (
      <div className="container-fluid">
        <div className="panel panel-default">
        <div className="panel-heading"><strong>Apylinkių sąrašas</strong></div>
          <table className="table">
            <thead>
              <tr>
                <th>Nr</th>
                <th>Pavadinimas</th>
                <th>Balsuotojų skaičius</th>
                <th>Apygarda</th>
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
