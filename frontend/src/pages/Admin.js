import {Link} from "react-router-dom";
import Users from "../components/Users";

const Admin = () => {
  return(
      <section>
          <h1>Admin page</h1>
          <Users />
          <div className={"flex-grow"}>
                <Link to={"/"}>Home</Link>
          </div>
      </section>
  )
}

export default Admin;