const React = require('react');
const NavLink = require('../navigation/nav-link');
const Link = require('react-router').Link;
const ReactBsTable = require("react-bootstrap-table");
const BootstrapTable = ReactBsTable.BootstrapTable;
const TableHeaderColumn = ReactBsTable.TableHeaderColumn;


const SingleMandateDistrictsComponent = React.createClass({
  render: function() {
    var self = this;
    var DistrictsList = [];

    self.props.districts.map(function(district, index) {
		    DistrictsList.push(
      		{
  				id: index+1,
          districtId: district.district.id,
  				name: district.district.name,
  				time: district.resultsDateString
  			}
  		);
	});

    return (
      
      <div>
      	<h4>Balsavimo rezultatai rinkimų apylinkėse</h4>
		<BootstrapTable height='auto' data={DistrictsList} striped={true} pagination search searchPlaceholder='ieškoti'  >
			<TableHeaderColumn width='35px' dataField='id' isKey>#</TableHeaderColumn>
        	<TableHeaderColumn  dataField='name'>Apylinkės</TableHeaderColumn>
        	<TableHeaderColumn  dataField='time'>Rezultatų pateikimo laikas</TableHeaderColumn>
		</BootstrapTable>
	</div>
    )
  }
});


module.exports = SingleMandateDistrictsComponent;