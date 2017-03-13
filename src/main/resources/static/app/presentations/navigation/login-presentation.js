const React = require('react');
const Link = require('react-router').Link;


var LoginComponent = React.createClass({
	render: function() {
		return (
		  <div className="container-fluid">
		    <h1 className="text-center">PRISIJUNKITE KAIP</h1>
				<br />
				<br />
				<br />
		    <div className="row-fluid">
		      <div className="text-center col-xs-6">
		        <div className="ih-item hop">
		          <Link to="/admin">
		            <div className="img">
		              <img src="img/admin.png" />
		            </div>
		            <div className="info">
		              <p>Administratorius</p>
		            </div>
		          </Link>
		        </div>
		      </div>
		      <div className="text-center col-xs-6">
		        <div className="ih-item hop">
		          <Link to="/representative">
		            <div className="img">
		              <img src="img/user.png" />
		            </div>
		            <div className="info">
		              <p>Atstovas</p>
		            </div>
		          </Link>
		        </div>
		      </div>
		    </div>
		  </div>
		);
	}
});

module.exports =  LoginComponent;
