import {makeStyles} from "@mui/styles";
import Card from "@mui/material/Card";
import {Button, CardMedia, Modal} from "@mui/material";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import {useState} from "react";
import Box from "@mui/material/Box";

const useStyles = makeStyles({
    root: {
        maxWidth: 345,
        margin: 50,
    }
})




function Avatar(){
    const classes = useStyles();
    const [open, setOpen] = useState(false);
    const handleOpen = () => {setOpen(true);};

    const handleClose = () => {setOpen(false);};


    return (
        <div>
        <Card className={classes.root}>
            <CardMedia
                sx={{ height: 270 }}
                image="/Avatar.png"
                title="avatar"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    Username
                </Typography>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                    Lizards are a widespread group of squamate reptiles, with over 6,000
                    species, ranging across all continents except Antarctica
                </Typography>
            </CardContent>
            <CardActions>
            <Button onClick={handleOpen}>Change</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box>
                    <Typography id="modal-modal-title" variant="h6" component="h2">
                        Text in a modal
                    </Typography>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                        Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
                    </Typography>
                </Box>
            </Modal>
            </CardActions>
        </Card>
        </div>
    );
}

export default Avatar;