import React, { useState, useEffect } from 'react';
//import ReactDOM from 'react-dom';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import {styled} from "@mui/material";
import {IconButtonProps} from "@mui/material/IconButton";
import {makeStyles} from "@mui/styles";
import {Link} from "react-router-dom";


const useStyle = makeStyles((theme) => ({
    root: {
        width: 800,
        textAlign: "left",
        margin: 20
    },
    media: {
        height: 0,
        paddingTop: '56.25%'
    },

    avatar: {
        background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
    },
    link: {
        textDecoration: "None",
        boxShadow: "None",
        color: "white"
    }
}));


interface ExpandMoreProps extends IconButtonProps {
    expand: boolean;
}

const ExpandMore = styled((props: ExpandMoreProps) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme }) => ({
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
    variants: [
        {
            props: ({ expand }) => !expand,
            style: {
                transform: 'rotate(0deg)',
            },
        },
        {
            props: ({ expand }) => !!expand,
            style: {
                transform: 'rotate(180deg)',
            },
        },
    ],
}));

function ServiceCompany(props) {

    const {title, description, price, duration, companyId, companyName} = props;
    const [expanded, setExpanded] = React.useState(false);
    const classes = useStyle();
    const [liked, setLiked] = useState(false);

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const handleLike = () => {
        setLiked(!liked);
    }

    return(
        <div className="serviceContainer">
            <Card className={classes.root}>
                <CardHeader
                    avatar={
                        <Link className={classes.link} to={{pathname : 'companies/' + companyId}}>
                        <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                            {companyName.charAt(0).toUpperCase()}
                        </Avatar>
                        </Link>
                    }

                    title={title}
                    subheader="September 14, 2016"
                />

                <CardContent>
                    <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                        {description}
                    </Typography>
                </CardContent>
                <CardActions disableSpacing>
                    <IconButton onClick={handleLike} aria-label="add to favorites">
                        <FavoriteIcon style={liked? {color: "red"}: null}/>
                    </IconButton>
                    <IconButton aria-label="share">
                        <ShareIcon />
                    </IconButton>
                    <ExpandMore
                        expand={expanded}
                        onClick={handleExpandClick}
                        aria-expanded={expanded}
                        aria-label="show more"
                    >
                        <ExpandMoreIcon />
                    </ExpandMore>
                </CardActions>
                <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <CardContent>

                    </CardContent>
                </Collapse>
            </Card>

        </div>
    )

}

export default ServiceCompany;