import React from 'react';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

export class Pagination extends React.Component {

    constructor() {
        super();
        this.changePage = this.changePage.bind(this);
    }

    changePage = (newPage) => {
        this.props.callBack(this.props.pagination.size, newPage);
    }

    handleChangeSize = (event) => {
        if (this.props.pagination.size !== event.target.value) {
            this.props.callBack(event.target.value, 0);
        }
    }

    render() {
        const { empty, first, last, number, totalElements, numberOfElements, size } = this.props.pagination;
        const sizeVariants = this.props.sizeVariants;
        if (!empty) {
            const btnDisableClassFirst = (first ? " Mui-disabled" : "");
            const btnDisableClassLast = (last ? " Mui-disabled" : "");
            const begin = size * number + 1;
            const end = size * number + numberOfElements;
            return (
                <>
                    <div className="MuiTablePagination-root">
                        <div className="MuiToolbar-root MuiToolbar-regular MuiTablePagination-toolbar MuiToolbar-gutters">
                            <div className="MuiInputBase-root MuiTablePagination-input MuiTablePagination-selectRoot paginationSelectSize">
                                <FormControl>
                                    <Select
                                        value={size}
                                        onChange={this.handleChangeSize}
                                        autoWidth
                                    >
                                        {sizeVariants.map((value) => {
                                            let nameForShow = (value === 2000 ? "Все" : value);
                                            return <MenuItem key={value} value={value}>{nameForShow}</MenuItem>;
                                        })}
                                    </Select>
                                </FormControl>
                            </div>

                            <span className="MuiTypography-root MuiTablePagination-caption MuiTypography-caption MuiTypography-colorInherit paginationElementsCount">
                                {begin}-{end} из {totalElements}
                            </span>
                            <div className="MuiTablePagination-actions">
                                <button className={"MuiButtonBase-root MuiIconButton-root MuiIconButton-colorInherit" + btnDisableClassFirst}
                                    type="button" disabled={first} aria-label="Previous Page" onClick={() => { this.changePage(number - 1); }}>
                                    <span className="MuiIconButton-label">
                                        <svg className="MuiSvgIcon-root" focusable="false" viewBox="0 0 24 24" aria-hidden="true" role="presentation">
                                            <path d="M15.41 16.09l-4.58-4.59 4.58-4.59L14 5.5l-6 6 6 6z"></path>
                                        </svg>
                                    </span>
                                </button>
                                <button className={"MuiButtonBase-root MuiIconButton-root MuiIconButton-colorInherit" + btnDisableClassLast}
                                    type="button" disabled={last} aria-label="Next Page" onClick={() => { this.changePage(number + 1); }}>
                                    <span className="MuiIconButton-label">
                                        <svg className="MuiSvgIcon-root" focusable="false" viewBox="0 0 24 24" aria-hidden="true" role="presentation">
                                            <path d="M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z"></path>
                                        </svg>
                                    </span>
                                    <span className="MuiTouchRipple-root"></span>
                                </button>
                            </div>
                        </div>
                    </div>
                </>
            );
        } else {
            return null;
        }
    }
}