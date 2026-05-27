import { Card } from "primereact/card";
import { useNavigate } from "react-router-dom";
import { Button } from "primereact/button";
import 'primeicons/primeicons.css';

const NotLoggedInComponent = () => {
    const navigate = useNavigate();

    return(
        <Card title="You are not logged in" subTitle="Please log in to view your tasks" className="p-card">
            <Card className="p-card-content">
                <Button
                    rounded
                    onClick={() => ( navigate('/login'))}>
                        Login Page
                </Button>
            </Card>
        </Card>
    );
}

export default NotLoggedInComponent;
