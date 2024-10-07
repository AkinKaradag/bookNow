import {makeStyles} from "@mui/styles";
import Card from "@mui/material/Card";
import {Button, CardMedia} from "@mui/material";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";



function Avatar(){

    return (
        <Card sx={{ maxWidth: 345 }}>
            <CardMedia
                sx={{ height: 140 }}
                image="../../../public/Avatar.png"
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
                <Button size="small">Change</Button>
                <Button size="small">Learn More</Button>
            </CardActions>
        </Card>
    );
}

export default Avatar;