using System.IO;
using System.Text.Json;
using graveyardAPI.Models;
using Microsoft.AspNetCore.Mvc;

namespace graveyardAPI.Controllers
{

    [Route("[controller]")]
    [ApiController]
    public class GraveyardController : ControllerBase
    {
        private GraveyardModel graveyardModel;
        private EdgesModel edgesModel;

        public GraveyardController()
        {
            using (var sr = new StreamReader("graveyard.json"))
            {
                string json = sr.ReadToEnd();
                graveyardModel = JsonSerializer.Deserialize<GraveyardModel>(json);
            }
            using (var sr = new StreamReader("edges.json"))
            {
                string json = sr.ReadToEnd();
                edgesModel = JsonSerializer.Deserialize<EdgesModel>(json);
            }
        }

        /// <summary>
        /// Get all information about the graveyard.
        /// </summary>
        [ProducesResponseType(typeof(GraveyardModel), 200)]
        [HttpGet()]
        public GraveyardModel GetGraveyard()
        {
            return graveyardModel;
        }

        /// <summary>
        /// Get all edges for the graveyard.
        /// </summary>
        [ProducesResponseType(typeof(EdgesModel), 200)]
        [HttpGet("/edges")]
        public EdgesModel GetEdges()
        {
            return edgesModel;
        }
    }

}