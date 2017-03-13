var EditRepresentativeComponent = React.createClass({
    render: function() {
        return (
              <form>
                <h4>Redaguoti atstovą</h4><br />
               
                <div className="form-group">
                    <label>Vardas</label>
                    <input className="form-control" type="text" value={this.props.representative.name} onChange={this.props.onFieldChange('name')} /><br />
                </div>
                <div className="form-group">
                    <label>Pavardė</label>
                    <input className="form-control" type="text" value={this.props.representative.surname} onChange={this.props.onFieldChange('surname')} /><br />
                </div>
                <div className="form-group">
                    <select className="form-control">
                        <option>Apylinkė</option>
                    </select>
                </div>
                <button className="btn btn-success btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onSaveClick}>Redaguoti</button>
                <button className="btn btn-danger btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onCancelClick}>Atšaukti</button>
            </form>
        );
    }
});

EditRepresentativeComponent.propTypes = {
    representative: React.PropTypes.object.isRequired,
    onFieldChange: React.PropTypes.func.isRequired,
    onSaveClick: React.PropTypes.func.isRequired
};

window.EditRepresentativeComponent = EditRepresentativeComponent;