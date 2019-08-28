import React from 'react';
import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';

function MadeWithLove() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Built with love by the '}
      <Link color="inherit" href="https://material-ui.com/">
        Material-UI
      </Link>
      {' and gulyaich.'}
    </Typography>
  );
}
export class Footer extends React.Component {
  render() {
    return (
        <>
        <MadeWithLove/>
        </>
    );
  }
}