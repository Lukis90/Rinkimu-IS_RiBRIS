var AddDistrictComponent = React.createClass({
    render: function() {

        var constituenciesList = this.props.constituencies.map(function(constituency, index) {
            return (
                <option key={index} value={constituency.id}>{constituency.name}</option>
            );
        });

        return (
            <form>
                <h4>Registruoti naują apylinkę</h4><br />
              
                <div className="form-group">
                    <label>Pavadinimas</label>
                    <input className="form-control" type="text" value={this.props.name} onChange={this.props.onNameChange} /><br />
                </div>
                <div className="form-group">
                    <label>Adresas</label>
                    <input className="form-control" type="text" value={this.props.address} onChange={this.props.onAddressChange} /><br />
                </div>
                <div className="form-group">
                    <label>Balsuotojų skaičius</label>
                    <input className="form-control" type="number" value={this.props.numOfVoters} onChange={this.props.onVotersChange} /><br />
                </div>
                   <div className="form-group">
                    <label>Priskirti apylinkę apygardai</label>
                    <select className="form-control" value={this.props.constituency}  onChange={this.props.HandleConstituencyChange}>
                            {constituenciesList}
                     </select>
                </div>
                <button className="btn btn-success btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onSaveClick}>Registruoti</button>
                <button className="btn btn-danger btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onCancelClick}>Atšaukti</button>
            </form>
        );
    }
});


window.AddDistrictComponent = AddDistrictComponent;