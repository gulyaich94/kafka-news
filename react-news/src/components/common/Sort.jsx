import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';

export class Sort extends React.Component {

    handleChangeSort = (event) => {
        if (this.props.mode !== event.target.value) {
            this.props.callBack(event.target.value);
        }
    }

    render() {
        return (
            <FormControl className={"customSortBlock"}>
                <InputLabel>Сортировка</InputLabel>
                <Select
                    value={this.props.mode}
                    onChange={this.handleChangeSort}
                    autoWidth
                >
                    <MenuItem value={10}>А-Я</MenuItem>
                    <MenuItem value={20}>Я-А</MenuItem>
                </Select>
            </FormControl>
        );
    }
}