import { Link } from 'react-router-dom'

export default function NotFound() {
    return (
        <div>
            <h2>
                Sorry! requested page not found
            </h2>
            <Link to='/'>
            <button className='btn btn-primary'>
                Back to Home Page
            </button>
            </Link>
        </div>
    )
}
