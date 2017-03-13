var React = require('react');
var Link = require('react-router').Link;

class NavLink extends React.Component {
  render() {
    return <Link  {...this.props} activeClassName="active" />
  }
}

module.exports = NavLink;
