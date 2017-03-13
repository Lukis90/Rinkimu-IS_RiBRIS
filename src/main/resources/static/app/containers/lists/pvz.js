const React = require('react');
const NavLink = require('../navigation/nav-link');
const Link = require('react-router').Link;

var SingleMandateComponent = React.createClass({
	render: function() {

		var nr = 1;
		var ConstituenciesList = this.props.constituencies.map(function(constituency, index) {
			var link = "/single-mandate-districts/" + constituency.id;
			return (
				<tr key={index}>
					<td><Link to={link}>{nr++}. {constituency.name}</Link></td>
				</tr>
			);
			
		});

		return (

			<div className="container-fluid">
				<div className="panel panel-default">
					<div className="panel-heading"><strong>Balsavimo rezultatai apygardose</strong></div>
					<table className="table table-striped table-bordered">
						<thead>
							<tr className="table-head"> 
								<th className="text-middle" style={{verticalAlign: 'middle'}} rowSpan="2">Apygardos</th> 
								<th colSpan="2">Apylinkių skaičius</th> 
							</tr> 
							<tr className="table-head"> 
								<th>iš viso</th> 
								<th>duomenis atsiuntė</th>
							</tr>
							</thead>
							<tbody> 
								{ConstituenciesList}
							</tbody> 
					</table>
				</div>
			</div>
     
					
		);


		return (

			<div className="container-fluid">
				<div className="panel panel-default">
					<div className="panel-heading"><strong>Balsavimo rezultatai apygardose</strong></div>
					<table className="table table-striped table-bordered">
						<thead>
							<tr className="table-head"> 
								<th className="text-middle" style={{verticalAlign: 'middle'}} rowSpan="2">Apygardos</th> 
								<th colSpan="2">Apylinkių skaičius</th> 
							</tr> 
							<tr className="table-head"> 
								<th>iš viso</th> 
								<th>duomenis atsiuntė</th>
							</tr>
							</thead>
							<tbody> 
								{ConstituenciesList}
							</tbody> 
					</table>
				</div>
			</div>
     
					
		);
	}
});

module.exports = SingleMandateComponent;

dataFormat={this.props.cellButton.bind(this)