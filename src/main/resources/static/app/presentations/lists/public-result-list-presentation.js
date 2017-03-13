const React = require('react');
const Link = require('react-router').Link;
const NavLink = require('../navigation/nav-link');

var PublicResultsListComponent = React.createClass({
	render: function() {
		return (
			<div>
				<div className="col-sm-2">
		    	<div className="side-menu">
		    		<nav className="navbar navbar-default" role="navigation">
		    			<div className="navbar-header">
		    				<div className="side-menu-container">
			      			<ul className="nav navbar-nav">
			              <li><Link to="/single-mandate"><i className="fa fa-user" aria-hidden="true"></i> Vienmandačių </Link></li>
			              <li><Link to="/multi-mandate"><i className="fa fa-users" aria-hidden="true"></i> Daugiamandačių </Link></li>
			            </ul>
			          </div>
			        </div>
			      </nav>
		      </div>
				</div>
				<div className="col-sm-10">
					{this.props.children}
				</div>
			</div>
		);
	}
});



module.exports = PublicResultsListComponent;
