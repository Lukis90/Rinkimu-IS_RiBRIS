var AddConstituencyComponent = React.createClass({
    render: function() {
        return (
            <form>
                <h4>Registruoti naują apygardą</h4><br />
                <div className="form-group">
                    <label>Pavadinimas</label>
                    <input className="form-control" type="text" required value={this.props.constituency.name} onChange={this.props.onFieldChange('name')} /><br />
                </div>
                <button className="btn btn-success btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onSaveClick}>Registruoti</button>
                <button className="btn btn-danger btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onCancelClick}>Atšaukti</button>
            </form>
        );
    }
});

AddConstituencyComponent.propTypes = {
    constituency: React.PropTypes.object.isRequired,
    onFieldChange: React.PropTypes.func.isRequired,
    onSaveClick: React.PropTypes.func.isRequired
};
window.AddConstituencyComponent = AddConstituencyComponent;