import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSignIn } from '@fortawesome/free-solid-svg-icons';

export default function Index( {estado, handleChange, handleSubmit}) {
    return (
        <div className="container mt-5">
            <div className="card">
            <div className="card-header">
                <h1 className="text-center">Alta de Carrera</h1>
            </div>
            <div className="card-body">
                <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="nombre" className="form-label"> Nombre:</label>
                    <input
                    type="text"
                    id="nombre"
                    name="nombre"
                    value={formData.nombre}
                    onChange={handleChange}
                    className="form-control"
                    required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="duracion" className="form-label">Duración:</label>
                    <input
                    type="text"
                    id="duracion"
                    name="duracion"
                    value={formData.duracion}
                    onChange={handleChange}
                    className="form-control"
                    required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="nivel" className="form-label">Nivel:</label>
                    <input
                    type="text"
                    id="nivel"
                    name="nivel"
                    value={formData.nivel}
                    onChange={handleChange}
                    className="form-control"
                    required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="descripcion" className="form-label">Descripción:</label>
                    <textarea
                    id="descripcion"
                    name="descripcion"
                    value={formData.descripcion}
                    onChange={handleChange}
                    className="form-control"
                    required
                    ></textarea>
                    <div></div>
                    <button type="submit" className="btn btn-primary">Guardar</button>
                </div>
                    
                    <div className="card-footer text-center">
                        <div className="small"><a href="./">Volver al inicio</a></div>
                    </div>
                </form>
            </div>
            </div>
        </div>
    );
}