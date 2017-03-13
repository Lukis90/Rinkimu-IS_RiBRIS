const React = require('react');

const SpoiledBallots = React.createClass({
  render: function() {
    return (
      <div className="row-fluid">
        <div className="col-sm-4" >
          <h4 className="alert alert-info">Sugadinti vienmandačiai biuleteniai</h4>
        </div>
        <div className="col-sm-2" >
          <h4 className="alert alert-success">{this.props.results.spoiledSingle}</h4>
        </div>
        <div className="col-sm-4">
          <h4 className="alert alert-info">Sugadinti daugiamandačiai biuleteniai</h4>
        </div>
        <div className="col-sm-2" >
          <h4 className="alert alert-success">{this.props.results.spoiledMulti}</h4>
        </div>
      </div>
    );
  }
});

module.exports = SpoiledBallots;
