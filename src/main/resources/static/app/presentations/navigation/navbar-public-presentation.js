const React = require('react');
const NavLink = require('../navigation/nav-link');

var PublicNavigation = React.createClass({
	render: function() {
		return (
		   <div className="container-fluid">
        <nav className="navbar navbar-default" role="navigation">
          <div className="collapse navbar-collapse">
            <ul className="nav navbar-nav">
            	<li><a href="#/"><i className="fa fa-home fa-2x" aria-hidden="true"></i></a></li>
              <li><a href="#/results">Rezultatai</a></li>
              <li><a href="#/activity">Aktyvumas </a></li>
            </ul>
            <ul className="nav navbar-nav navbar-right">
            	 <li><a href="#/login">
            	 	<i className="fa fa-sign-in" aria-hidden="true"></i> Prisijungti
            	 </a></li>
            </ul>
          </div>
        </nav>
	      <div className="row-fluid">
	        {this.props.children}
	      </div>

	    </div>
		);
	}
});



module.exports = PublicNavigation;
