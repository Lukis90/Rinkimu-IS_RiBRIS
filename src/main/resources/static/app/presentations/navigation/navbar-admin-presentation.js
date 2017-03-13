const React = require('react');
const NavLink = require('./nav-link');
const Modal = require('react-bootstrap').Modal;
const Button = require('react-bootstrap').Button;

var Navigation = React.createClass({
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
                <li><NavLink to="/"><i className="fa fa-home fa-2x" aria-hidden="true"></i></NavLink></li>
                <li><NavLink to="/admin/constituencies">Apygardos</NavLink></li>
                <li><NavLink to="/admin/districts">Apylinkes</NavLink></li>
                <li><NavLink to="/admin/parties">Partijos</NavLink></li>
                <li><NavLink to="/admin/candidates">Kandidatai</NavLink></li>
                <li><NavLink to="/admin/representatives">Atstovai</NavLink></li>
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

module.exports = Navigation;
