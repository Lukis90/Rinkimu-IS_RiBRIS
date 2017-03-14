const React = require('react');
const router = require('react-router');


var DistrictsActivityListComponent = React.createClass({
	handleClick: function(e) {
	    this.router.transitionTo('index');
	  },
	
	render: function() {
    var self = this;
    var nr = 1;
    var activityList = this.props.districts.map(function(district, index) {
    	return (
        <tr key={index}>
          <td>{nr++}</td>
          <td>{district.district.name}</td>
          <td>{district.givenBallots}</td>
          <td>{district.percentOfAllVoters}%</td>
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

module.exports = DistrictsActivityListComponent;
