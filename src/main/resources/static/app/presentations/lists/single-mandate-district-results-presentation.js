const React = require('react');
const NavLink = require('../navigation/nav-link');
const Link = require('react-router').Link;

var SingleMandateDistrictResultsComponent = React.createClass({
	render: function() {

		return (

			<div className="container-fluid">
				<div className="panel panel-default">
					<div className="panel-heading"><strong>Balsavimo rezultatai rinkimų apylinkėje</strong></div>
					<table className="table table-striped table-bordered">
						<thead>
							<tr className="table-head"> 
								<th className="text-middle" style={{verticalAlign: 'middle'}} rowSpan="2">Kandidatas</th> 
								<th className="text-middle" style={{verticalAlign: 'middle'}} rowSpan="2">Iškėlė</th>
								<th className="text-middle" colSpan="2">Paduotų balsų skaičius</th>
							</tr> 
							<tr className="table-head"> 
								<th>iš viso</th> 
								<th>% nuo galiojančių biuletenių</th>
								<th>% nuo dalyvavusių rinkėjų</th> 
							</tr>
							</thead>
							<tbody> 
								
							</tbody> 
					</table>
				</div>
			</div>
     
					
		);
	}
});

module.exports = SingleMandateDistrictResultsComponent;
