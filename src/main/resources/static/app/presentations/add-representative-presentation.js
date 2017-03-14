const React = require('react');
const TextValidator = require('../util/validation/text-validator-container');
const Alert = require('../util/alert/alert');

var AddRepresentativePresentation = React.createClass({
  render: function() {
    if(this.props.representativeId == undefined) {
      return (
        <div className="container-fluid">
          <Alert text={this.props.text} style={"alert alert-danger alert-dismissable text-center"} />
          <form autoComplete="off">
            <h4 className="alert alert-info">Registruoti naują atstovą</h4>
            <br />
            <div className="form-group">
              <label>Vardas</label>
              <TextValidator
                handleValidStateChange={this.props.handleValidStateChange}
                >
                <input className="form-control" type="text" value={this.props.name} onChange={this.props.onNameChange} />
              </TextValidator>
            </div>
            <div className="form-group">
              <label>Pavardė</label>
              <TextValidator
                handleValidStateChange={this.props.handleValidStateChange}
                >
                <input className="form-control" type="text" value={this.props.surname} onChange={this.props.onSurnameChange} />
              </TextValidator>
            </div>
            <div className="form-group">
              <h4 className="alert alert-info">Atstovas bus priskirtas <b>{this.props.districtName}</b> apylinkei</h4>
            </div>
            <button className="btn btn-success btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onSaveClick}>Priskirti</button>
            <button className="btn btn-danger btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onCancelClick}>Atšaukti</button>
          </form>
        </div>
      );
    } else {
      return (
        <div className="container-fluid">
          <Alert text={this.props.text} style={"alert alert-danger alert-dismissable text-center"} />
          <div className="col-sm-offset-1 col-sm-10">
            <form autoComplete="off">
              <h4 className="alert alert-info">Redaguoti {this.props.districtName} apylinkės atstovą</h4>
              <br />
              <div className="form-group">
                <label>Vardas</label>
                <TextValidator
                  handleValidStateChange={this.props.handleValidStateChange}
                  >
                  <input className="form-control" type="text" value={this.props.name} onChange={this.props.onNameChange} />
                </TextValidator>
              </div>
              <div className="form-group">
                <label>Pavardė</label>
                <TextValidator
                  handleValidStateChange={this.props.handleValidStateChange}
                  >
                  <input className="form-control" type="text" value={this.props.surname} onChange={this.props.onSurnameChange} />
                </TextValidator>
              </div>
              <button className="btn btn-success btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onSaveClick}>Redaguoti</button>
              <button className="btn btn-danger btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onCancelClick}>Atšaukti</button>
            </form>
        </div>
        </div>
      );
    }
  }
});

module.exports = AddRepresentativePresentation;
