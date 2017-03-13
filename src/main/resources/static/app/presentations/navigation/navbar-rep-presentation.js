const React = require('react');
const NavLink = require('./nav-link');

var NavigationRep = React.createClass({
  render: function() {
    var self = this;

    var SideNav = this.props.options.map(function(option, index) {
      return (
        <li key={index}>
          <NavLink to={option.pathTo}>{option.text}</NavLink>
        </li>
      );
    });

    return (
      <div className="container">
        <div className="navbar-inner nav-collapse">
          <nav className="navbar navbar-default" role="navigation">
            <div className="collapse navbar-collapse">
              <ul className="nav navbar-nav">
              <li><NavLink to="/representative/results/single">Rezultatai</NavLink></li>
              </ul>
              <ul className="nav navbar-nav navbar-right">
                <li><NavLink to="/">
                  <i className="fa fa-sign-in" aria-hidden="true"></i> Atsijungti
                </NavLink></li>
              </ul>
            </div>
          </nav>
        </div>
        <div id='content' className='row-fluid'>
          <div className="col-sm-2">
            <ul className="nav nav-tabs nav-stacked">
              {SideNav}
            </ul>
          </div>
          <div className="col-sm-10">
            {this.props.childs}
          </div>
        </div>
      </div>
    );
  }
});

module.exports = NavigationRep;
