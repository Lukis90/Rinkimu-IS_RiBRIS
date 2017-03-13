const React = require('react');
const NavLink = require('../navigation/nav-link');

var MultiMandateComponent = React.createClass({
	render: function() {
		return (

				<div className="container-fluid col-md-10">
					<div className="panel panel-default">
						<div className="panel-heading"><strong>Daugiamandatės rezultatai</strong></div>
						<table className="table">
							<thead>
								<tr>
									<th>Nr</th>
									<th>Laukas</th>
									<th>Laukas</th>
									<th>Laukas</th>
									<th>Laukas</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>Lauko duomenys</td>
									<td>Lauko duomenys</td>
									<td>Lauko duomenys</td>
									<td>Lauko duomenys</td>
								</tr>
								<tr>
									<td>2</td>
									<td>Lauko duomenys</td>
									<td>Lauko duomenys</td>
									<td>Lauko duomenys</td>
									<td>Lauko duomenys</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
		
		);
	}
});

module.exports = MultiMandateComponent;
